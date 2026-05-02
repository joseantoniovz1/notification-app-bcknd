package com.notification.dto;

import com.notification.model.Category;
import lombok.Data;

@Data
public class NotificationRequest {

    private Category category;
    private String message;

}
