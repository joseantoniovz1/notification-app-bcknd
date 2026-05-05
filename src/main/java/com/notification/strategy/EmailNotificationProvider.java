package com.notification.strategy;

import com.notification.model.Channel;
import com.notification.model.Message;
import com.notification.model.NotificationEvent;
import com.notification.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Log4j2
@Component
@RequiredArgsConstructor
public class EmailNotificationProvider implements NotificationProvider {

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    @Override
    public Channel getChannel() {
        return Channel.EMAIL;
    }

    @Override
    public void sendMessage(User user, Message message) {
        log.info("Sending email to {}: {}", user.getEmail(), message.getContent());
        NotificationEvent notificationEvent = NotificationEvent.builder()
                .id(user.getId())
                .userName(user.getEmail())
                .category(message.getCategory())
                .message(message.getContent())
                .timestamp(LocalDateTime.now())
                .build();
        kafkaTemplate.send("email-topic", notificationEvent);
        log.info("Published EMAIL event to Kafka");
    }

}
