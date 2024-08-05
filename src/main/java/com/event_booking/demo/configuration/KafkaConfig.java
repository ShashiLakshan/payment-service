package com.event_booking.demo.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    public NewTopic createTopic() {
        return new NewTopic("event-topic", 3, (short) 1);
    }
}
