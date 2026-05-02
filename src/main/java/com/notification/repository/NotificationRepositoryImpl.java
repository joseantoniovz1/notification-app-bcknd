package com.notification.repository;

import com.notification.model.Category;
import com.notification.model.Channel;
import com.notification.model.Notification;
import com.notification.model.NotificationStatus;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {

    @Override
    public List<Notification> getNotifications() {

        return Arrays.asList(
        Notification.builder()
                .id(1L)
                .channel(Channel.PUSH)
                .timestamp(LocalDateTime.now())
                .notificationStatus(NotificationStatus.SENT)
                .message("Hello World!")
                .category(Category.SPORTS)
                .userName("user")
                .build(),
        Notification.builder()
                .id(2L)
                .channel(Channel.EMAIL)
                .timestamp(LocalDateTime.now())
                .notificationStatus(NotificationStatus.FAILED)
                .message("Hello World!")
                .category(Category.SPORTS)
                .userName("user")
                .build(),
        Notification.builder()
                .id(3L)
                .channel(Channel.PUSH)
                .timestamp(LocalDateTime.now())
                .notificationStatus(NotificationStatus.SENT)
                .message("Hello World!")
                .category(Category.SPORTS)
                .userName("user")
                .build(),
        Notification.builder()
                .id(4L)
                .channel(Channel.PUSH)
                .timestamp(LocalDateTime.now())
                .notificationStatus(NotificationStatus.FAILED)
                .message("Hello World!")
                .category(Category.SPORTS)
                .userName("user")
                .build()
        );
    }
}
