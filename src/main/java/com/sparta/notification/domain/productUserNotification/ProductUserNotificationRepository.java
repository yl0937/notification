package com.sparta.notification.domain.productUserNotification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductUserNotificationRepository extends JpaRepository<ProductUserNotification, Long> {
}
