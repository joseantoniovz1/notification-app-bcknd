package com.notification.controller;

import com.notification.advice.GlobalExceptionHandler;
import com.notification.dto.NotificationRequest;
import com.notification.dto.NotificationResponse;
import com.notification.exception.BadRequestException;
import com.notification.model.Category;
import com.notification.model.Message;
import com.notification.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationControllerTest {

    @InjectMocks
    private NotificationController notificationController;

    @Mock
    private NotificationService notificationService;

    @Test
    void shouldSendNotificationSuccessfully() {
        NotificationRequest request = new NotificationRequest();
        request.setMessage("Hello");
        request.setCategory(Category.FINANCE);

        ResponseEntity<String> response = notificationController.sendNotification(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Notification sent successfully", response.getBody());
        verify(notificationService, times(1)).processNotification(any(Message.class));
    }

    @Test
    void shouldReturnNotificationHistory() {
        List<NotificationResponse> mockList = List.of(NotificationResponse.builder().build());
        when(notificationService.getNotifications()).thenReturn(mockList);

        List<NotificationResponse> response = notificationController.getAllNotifications();

        assertEquals(1, response.size());
        verify(notificationService).getNotifications();
    }

    @Test
    void shouldHandleBadRequestException() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        ResponseEntity<Map<String, String>> response =
                handler.handleBadRequest(new BadRequestException("Message cannot be empty"));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Message cannot be empty", response.getBody().get("error"));
    }

}