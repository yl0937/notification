package com.sparta.notification.domain.productUserNotificationHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationHistory extends JpaRepository<ProductUserNotificationHistory, Long> {
}
