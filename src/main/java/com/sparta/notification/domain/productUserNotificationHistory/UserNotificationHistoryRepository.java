package com.sparta.notification.domain.productUserNotificationHistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationHistoryRepository extends JpaRepository<ProductUserNotificationHistory, Long> {
}
