package com.event_booking.demo.service;

import com.event_booking.demo.dto.PaymentDto;
import com.event_booking.demo.marker_interfaces.CreateMarker;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface PaymentService {

    @Validated(CreateMarker.class)
    PaymentDto createPayment(@Valid PaymentDto paymentDto);

}
