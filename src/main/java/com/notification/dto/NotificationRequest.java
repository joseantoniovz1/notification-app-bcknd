package com.notification.dto;

import com.notification.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationRequest {

    @NotNull
    private Category category;

    @NotBlank
    private String message;

}
