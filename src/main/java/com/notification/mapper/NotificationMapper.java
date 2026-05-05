package com.notification.mapper;

import com.notification.dto.NotificationResponse;
import com.notification.model.Notification;

public class NotificationMapper {

    public static NotificationResponse toNotificationResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .userName(notification.getUserName())
                .message(notification.getMessage())
                .category(notification.getCategory())
                .channel(notification.getChannel())
                .notificationStatus(notification.getNotificationStatus())
                .timestamp(notification.getTimestamp())
                .build();
    }

}
