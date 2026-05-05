package com.notification.service;

import com.notification.dto.NotificationResponse;
import com.notification.factory.NotificationProviderFactory;
import com.notification.model.*;
import com.notification.repository.NotificationRepository;
import com.notification.repository.UserRepository;
import com.notification.strategy.NotificationProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Mock
    private NotificationProviderFactory providerFactory;

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationProvider provider;

    @Test
    void shouldSendNotificationToSubscribedUsers() {
        User user = User.builder()
                        .name("John")
                        .subscribed(List.of(Category.FINANCE))
                        .channels(List.of(Channel.EMAIL))
                        .build();

        when(userRepository.getAllUsers()).thenReturn(List.of(user));
        when(providerFactory.getProvider(Channel.EMAIL)).thenReturn(provider);

        Message message = Message.builder()
                .category(Category.FINANCE)
                .content("Test")
                .build();

        notificationService.processNotification(message);

        verify(provider).sendMessage(eq(user), eq(message));
    }

    @Test
    void shouldHandleProviderFailure() {
        User user = User.builder()
                .name("John")
                .subscribed(List.of(Category.SPORTS))
                .channels(List.of(Channel.EMAIL))
                .build();

        when(userRepository.getAllUsers()).thenReturn(List.of(user));
        when(providerFactory.getProvider(Channel.EMAIL)).thenReturn(provider);
        doThrow(new RuntimeException()).when(provider).sendMessage(ArgumentMatchers.any(), ArgumentMatchers.any());

        Message message = Message.builder()
                .category(Category.SPORTS)
                .content("Test")
                .build();

        notificationService.processNotification(message);

        verify(notificationRepository).save(argThat(n ->
                n.getNotificationStatus() == NotificationStatus.FAILED
        ));
    }

    @Test
    void shouldReturnNotificationsSortedByTimestampDesc() {
        Notification older = new Notification();
        older.setTimestamp(LocalDateTime.of(2024, 1, 1, 10, 0));
        older.setMessage("Old");

        Notification newer = new Notification();
        newer.setTimestamp(LocalDateTime.of(2024, 1, 2, 10, 0));
        newer.setMessage("New");

        when(notificationRepository.findAll())
                .thenReturn(List.of(older, newer));

        List<NotificationResponse> result = notificationService.getNotifications();

        assertEquals(2, result.size());

        assertEquals("New", result.get(0).getMessage());
        assertEquals("Old", result.get(1).getMessage());

        verify(notificationRepository).findAll();
    }

    @Test
    void shouldReturnEmptyListWhenNoNotifications() {
        when(notificationRepository.findAll()).thenReturn(Collections.emptyList());

        List<NotificationResponse> result = notificationService.getNotifications();

        assertTrue(result.isEmpty());
    }

}