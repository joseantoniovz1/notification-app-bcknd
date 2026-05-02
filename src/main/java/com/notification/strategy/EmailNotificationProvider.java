package com.notification.strategy;

import com.notification.model.Channel;
import com.notification.model.Message;
import com.notification.model.User;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationProvider implements NotificationProvider {


    @Override
    public Channel getChannel() {
        return Channel.EMAIL;
    }

    @Override
    public void sendMessage(User user, Message message) {
        System.out.println("Sending email to " + user.getEmail() +": " + message.getContent());
    }


}
