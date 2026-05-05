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
public class SmsNotificationProvider implements NotificationProvider {

    private final KafkaTemplate<String, NotificationEvent> kafkaTemplate;

    @Override
    public Channel getChannel() {
        return Channel.SMS;
    }

    @Override
    public void sendMessage(User user, Message message) {
        log.info("Sending SMS to {}: {}", user.getPhoneNumber(), message.getContent());
        NotificationEvent notificationEvent = NotificationEvent.builder()
                .id(user.getId())
                .userName(user.getEmail())
                .category(message.getCategory())
                .message(message.getContent())
                .timestamp(LocalDateTime.now())
                .build();
        kafkaTemplate.send("sms-topic", notificationEvent);
        log.info("Published SMS event to Kafka");
    }

}
