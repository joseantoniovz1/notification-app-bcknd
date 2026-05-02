package com.notification.controller;

import com.notification.dto.NotificationRequest;
import com.notification.model.Category;
import com.notification.model.Channel;
import com.notification.model.Message;
import com.notification.model.Notification;
import com.notification.service.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message cannot be empty");
        }

        Message message = Message.builder()
                .category(Category.valueOf(notificationRequest.getCategory().name()))
                .content(notificationRequest.getMessage())
                .build();

        notificationService.processNotification(message);
        return ResponseEntity.ok().body("Notification sent successfully");
    }

    @GetMapping("/history")
    public List<Notification> getAllNotifications() {
        log.info("Getting all history");
        return notificationService.getNotifications();
    }

}
