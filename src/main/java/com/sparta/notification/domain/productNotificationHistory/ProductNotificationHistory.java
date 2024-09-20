package com.sparta.notification.domain.productNotificationHistory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "ProductNotificationHistory")
public class ProductNotificationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private String status;
    private Long userId;

    public static ProductNotificationHistory from (Long productId, Long userId, String status) {
        return ProductNotificationHistory.builder()
                .productId(productId)
                .userId(userId)
                .status(status)
                .build();
    }
    public void updateStatus(String status) {
        this.status = status;
    }
    public void updateUserId(Long userId) {
        this.userId = userId;
    }
}
