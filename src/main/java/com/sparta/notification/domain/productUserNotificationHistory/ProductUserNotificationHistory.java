package com.sparta.notification.domain.productUserNotificationHistory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProductUserNotificationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Long userId;
    private Integer restock;
    private LocalDateTime sendAt;

    public static ProductUserNotificationHistory from (Long productId, Long userId, Integer restock) {
        return ProductUserNotificationHistory.builder()
                .productId(productId)
                .userId(userId)
                .restock(restock)
                .sendAt(LocalDateTime.now())
                .build();
    }
}
