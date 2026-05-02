package com.notification.repository;

import com.notification.model.Notification;

import java.util.List;

public interface NotificationRepository {

    List<Notification> getNotifications();

}
