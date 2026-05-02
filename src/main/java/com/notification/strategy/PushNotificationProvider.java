package com.notification.strategy;

import com.notification.model.Channel;
import com.notification.model.Message;
import com.notification.model.User;
import org.springframework.stereotype.Component;

@Component
public class PushNotificationProvider implements NotificationProvider {


    @Override
    public Channel getChannel() {
        return Channel.PUSH;
    }

    @Override
    public void sendMessage(User user, Message message) {
        System.out.println("Sending push notification to " + user.getName() + ": " + message.getContent());
    }

}
