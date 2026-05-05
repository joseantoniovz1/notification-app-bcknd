package com.notification.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NotificationEvent {

    private Long id;
    private String userName;
    private Category category;
    private String message;
    private LocalDateTime timestamp;

}
