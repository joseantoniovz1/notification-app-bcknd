package com.notification.dto;

import com.notification.model.Category;
import com.notification.model.Channel;
import com.notification.model.NotificationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationResponse {

    private Long id;
    private String userName;
    private Category category;
    private Channel channel;
    private String message;
    private NotificationStatus notificationStatus;
    private LocalDateTime timestamp;

}
