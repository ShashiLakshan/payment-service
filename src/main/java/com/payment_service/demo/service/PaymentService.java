package com.payment_service.demo.service;

import com.payment_service.demo.dto.PaymentDto;
import com.payment_service.demo.marker_interfaces.CreateMarker;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface PaymentService {

    @Validated(CreateMarker.class)
    PaymentDto createPayment(@Valid PaymentDto paymentDto);

}
