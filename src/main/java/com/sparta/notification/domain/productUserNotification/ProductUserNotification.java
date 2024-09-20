package com.sparta.notification.domain.productUserNotification;

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
@Entity(name = "ProductUserNotification")
public class ProductUserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long productId;
    private Long userId;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProductUserNotification from(Long productId, Long userId) {
        return ProductUserNotification.builder()
                .productId(productId)
                .userId(userId)
                .status("activate")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
