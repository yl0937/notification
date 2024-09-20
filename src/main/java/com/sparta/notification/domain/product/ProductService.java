package com.sparta.notification.domain.product;

import com.google.common.util.concurrent.RateLimiter;
import com.sparta.notification.common.exception.BaseException;
import com.sparta.notification.common.exception.ErrorCode;
import com.sparta.notification.domain.productNotificationHistory.ProductNotificationHistory;
import com.sparta.notification.domain.productNotificationHistory.ProductNotificationHistoryRepository;
import com.sparta.notification.domain.productUserNotification.NotificationService;
import com.sparta.notification.domain.productUserNotification.ProductUserNotification;
import com.sparta.notification.domain.productUserNotification.ProductUserNotificationRepository;
import com.sparta.notification.domain.productUserNotificationHistory.UserNotificationHistoryRepository;
import com.sparta.notification.domain.productUserNotificationHistory.ProductUserNotificationHistory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductUserNotificationRepository userNotificationRepository;
    private final NotificationService notificationService;
    private final UserNotificationHistoryRepository historyRepository;
    private final ProductNotificationHistoryRepository notificationHistoryRepository;

    private final RateLimiter rateLimiter = RateLimiter.create(500.0);

    public void sendNotification(Product product, Long productId, List<ProductUserNotification> users){
        // 재입고 알림 전송 상태 생성 (발송중)
        ProductNotificationHistory notificationHistory = notificationHistoryRepository.save(
                ProductNotificationHistory.from(productId,users.get(0).getUserId(),"IN_PROGRESS"));

        boolean completed = true;
        for (ProductUserNotification user : users) {
            if(product.getCount() <= 0 ) {
                // 품절에 의한 발송 중단
                notificationHistory.updateStatus("CANCELED_BY_SOLD_OUT");
                notificationHistory.updateUserId(user.getUserId());
                notificationHistoryRepository.save(notificationHistory);
                completed = false;
                break;
            }

            boolean success = notificationService.send(user.getUserId());

            if (success) {
                // 재고 감소
                product.minusCount();

                // 초당 요청 수 제한
                rateLimiter.acquire();

                // 알림 받은 유저 저장
                historyRepository.save(ProductUserNotificationHistory.from(productId,user.getUserId(),product.getRestock()));
            }
            else {
                //중단된 시점 반영 - 예외에 의한 발송 중단
                notificationHistory.updateStatus("CANCELED_BY_ERROR");
                notificationHistory.updateUserId(user.getUserId());
                notificationHistoryRepository.save(notificationHistory);
                completed = false;
                break;
            }

        }

        // 재고 반영
        productRepository.save(product);

        if (completed) {
            notificationHistory.updateStatus("COMPLETED");
            notificationHistoryRepository.save(notificationHistory);
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void productRestock(Long productId, Integer count) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new BaseException(ErrorCode.WRONG_PRODUCT));

        // 재고 반영 및 재입고 회차 증가
        product.updateCount(count);
        product.plusRestock();
        productRepository.save(product);

        // 알림 유저 리스트 조회
        List<ProductUserNotification> users = userNotificationRepository.findByProductIdOrderByIdAsc(productId);

        // 알림 전송
        sendNotification(product,productId,users);

    }

    public void manualRestock(Long productId) {
        // 전송해야 할 유저가 알림 설정한 시간 저장
        ProductNotificationHistory notificationHistory = notificationHistoryRepository.findByProductId(productId);
        Long userId = notificationHistory.getUserId();
        LocalDateTime time = userNotificationRepository.findByProductIdAndUserId(productId,userId).getCreatedAt();

        // 저장된 시간 이후의 데이터만 가져오기
        List<ProductUserNotification> users =
                userNotificationRepository.findByProductIdOrderByIdAscAfter(productId,time);

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new BaseException(ErrorCode.WRONG_PRODUCT));

        // 알림 전송
        sendNotification(product,productId,users);

    }

}
