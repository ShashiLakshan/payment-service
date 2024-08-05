package com.event_booking.demo.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    public NewTopic createEventTopic() {
        return new NewTopic("event-topic", 3, (short) 1);
    }

    public NewTopic createNotificationTopic() {
        return new NewTopic("notification-topic", 3, (short) 1);
    }
}
