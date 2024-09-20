package com.sparta.notification.domain.product;

import com.sparta.notification.common.exception.BaseException;
import com.sparta.notification.common.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void productRestock(Long productId, Integer count) {
        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new BaseException(ErrorCode.WRONG_PRODUCT));

        product.updateCount(count);
        product.plusRestock();
        productRepository.save(product);
    }

}
