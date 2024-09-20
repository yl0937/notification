package com.sparta.notification.domain.productUserNotification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductUserNotificationRepository extends JpaRepository<ProductUserNotification, Long> {
    List<ProductUserNotification> findByProductIdOrderByIdAsc(Long productId);
    ProductUserNotification findByProductIdAndUserId(Long productId, Long userId);
    @Query("SELECT p FROM ProductUserNotification p WHERE p.createdAt >= :dateTime and p.productId = :productId")
    List<ProductUserNotification> findByProductIdOrderByIdAscAfter(Long productId, LocalDateTime dateTime);
}
