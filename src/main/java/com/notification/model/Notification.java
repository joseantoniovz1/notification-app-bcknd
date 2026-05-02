package com.notification.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Notification {

    private Long id;
    private String userName;
    private Category category;
    private Channel channel;
    private String message;
    private NotificationStatus notificationStatus;
    private LocalDateTime timestamp;

}
