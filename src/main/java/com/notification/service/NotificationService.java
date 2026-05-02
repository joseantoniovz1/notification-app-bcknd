package com.notification.service;


import com.notification.model.Channel;
import com.notification.model.Message;
import com.notification.model.Notification;
import com.notification.model.User;
import com.notification.repository.NotificationRepository;
import com.notification.repository.UserRepository;
import com.notification.strategy.NotificationProvider;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {

    private List<NotificationProvider> providers;
    private NotificationRepository notificationRepository;

    public void processNotification(Message message) {

        List<User> users = UserRepository.getAllUsers();

        users.stream()
                .filter(user -> user.getSubscribed().contains(message.getCategory()))
                .forEach(user -> {
                    for (Channel channel : user.getChannels()) {
                        triggerProvider(channel, user, message);

                    }
                });
    }

    private void triggerProvider(Channel channel, User user, Message message) {
        providers.stream()
                .filter(provider -> provider.getChannel().equals(channel))
                .findFirst()
                .ifPresent(provider -> {
                    provider.sendMessage(user, message);
                    // Log the result to H2 database
                    // repository.save()
                });
    }

    public List<Notification> getNotifications() {
        return notificationRepository.getNotifications();
    }

}
