package com.event_booking.demo.service;

import com.event_booking.demo.dto.PaymentEventDto;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class KafkaPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendEventToTopic(String topic, PaymentEventDto paymentEventDto) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, paymentEventDto);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Message sent successfully " + paymentEventDto.toString());
            } else {
                System.out.println("Error sending message " + paymentEventDto.toString() );
            }
        });
    }
}
