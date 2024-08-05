package com.payment_service.demo.controller;

import com.payment_service.demo.dto.PaymentDto;
import com.payment_service.demo.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<PaymentDto> createPayment(@RequestBody @Valid PaymentDto paymentDto) {
        PaymentDto response = paymentService.createPayment(paymentDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
