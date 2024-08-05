package com.payment_service.demo.feign_client;

import com.payment_service.demo.configuration.FeignClientConfig;
import com.payment_service.demo.dto.inter_service.BookingDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "event-service", url = "${booking.service.url}", configuration = FeignClientConfig.class)
public interface BookingClient {

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<BookingDto> getBookingById(@PathVariable Integer id);
}
