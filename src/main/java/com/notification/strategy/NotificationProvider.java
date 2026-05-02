package com.notification.strategy;

import com.notification.model.Channel;
import com.notification.model.Message;
import com.notification.model.User;

public interface NotificationProvider {

    Channel getChannel();
    void sendMessage(User user,  Message message);

}
