package com.notification.service;


import com.notification.dto.NotificationResponse;
import com.notification.factory.NotificationProviderFactory;
import com.notification.mapper.NotificationMapper;
import com.notification.model.*;
import com.notification.repository.NotificationRepository;
import com.notification.repository.UserRepository;
import com.notification.strategy.NotificationProvider;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@AllArgsConstructor
public class NotificationService {

    private NotificationProviderFactory notificationProviderFactory;
    private NotificationRepository notificationRepository;
    private UserRepository userRepository;

    public void processNotification(Message message) {
        log.info("Processing notification");
        List<User> users = userRepository.getAllUsers();

        users.stream()
                .filter(user -> user.getSubscribed().contains(message.getCategory()))
                .forEach(user -> {
                    for (Channel channel : user.getChannels()) {
                        triggerProvider(channel, user, message);

                    }
                });
    }

    private void triggerProvider(Channel channel, User user, Message message) {
        NotificationProvider provider = notificationProviderFactory.getProvider(channel);

        Notification notification = new Notification();
        notification.setChannel(channel);

        try {
            provider.sendMessage(user, message);
            notification.setNotificationStatus(NotificationStatus.SENT);
        } catch (Exception e) {
            log.error("Error sending notification", e);
            notification.setNotificationStatus(NotificationStatus.FAILED);
        }
        persistNotification(notification, user, message);
    }

    public List<NotificationResponse> getNotifications() {
        log.info("Retrieving notifications");

        return notificationRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Notification::getTimestamp).reversed())
                .map(NotificationMapper::toNotificationResponse)
                .collect(Collectors.toList());
    }

    private void persistNotification(Notification notification, User user, Message message) {
        log.info("Persisting notification");
        notification.setUserName(user.getName());
        notification.setCategory(message.getCategory());
        notification.setMessage(message.getContent());
        notification.setTimestamp(LocalDateTime.now());
        notificationRepository.save(notification);
    }

}
