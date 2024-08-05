package com.event_booking.demo.feign_client;

import com.event_booking.demo.configuration.FeignClientConfig;
import com.event_booking.demo.dto.inter_service.EventDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "event-service", url = "${event.service.url}", configuration = FeignClientConfig.class)
public interface EventClient {

    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<EventDto> getEventById(@PathVariable Integer id);
}
