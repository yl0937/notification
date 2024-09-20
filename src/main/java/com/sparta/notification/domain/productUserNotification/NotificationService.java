package com.sparta.notification.domain.productUserNotification;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public boolean send(Long userId) {
        try {
            System.out.println("Sending notification to : " + userId);
            return true;
        } catch (Exception e) {
            System.out.println("Failed to send notification to : " + userId);
            return false;
        }

    }

}
