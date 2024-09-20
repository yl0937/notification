package com.sparta.notification.domain.productUserNotification;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public boolean send(Long userId) {
        System.out.println("Sending notification to : " + userId);
        return true;
    }

}
