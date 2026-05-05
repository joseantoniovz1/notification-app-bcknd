package com.notification.controller;

import com.notification.dto.NotificationRequest;
import com.notification.dto.NotificationResponse;
import com.notification.exception.BadRequestException;
import com.notification.model.Category;
import com.notification.model.Message;
import com.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@CrossOrigin
@AllArgsConstructor
@RestController
@RequestMapping("api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest notificationRequest) {
        log.info("Sending notification");
        if (notificationRequest.getMessage() == null || notificationRequest.getMessage().isEmpty()) {
            throw new BadRequestException("Message cannot be empty");
        }

        Message message = Message.builder()
                .category(Category.valueOf(notificationRequest.getCategory().name()))
                .content(notificationRequest.getMessage())
                .build();

        notificationService.processNotification(message);
        return ResponseEntity.ok().body("Notification sent successfully");
    }

    @GetMapping("/history")
    public List<NotificationResponse> getAllNotifications() {
        log.info("Getting all history");
        return notificationService.getNotifications();
    }

}
