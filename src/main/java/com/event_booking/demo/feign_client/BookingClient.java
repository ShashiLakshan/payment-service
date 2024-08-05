package com.event_booking.demo.feign_client;

import com.event_booking.demo.configuration.FeignClientConfig;
import com.event_booking.demo.dto.inter_service.BookingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "event-service", url = "${booking.service.url}", configuration = FeignClientConfig.class)
public interface BookingClient {

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Integer id);
}
