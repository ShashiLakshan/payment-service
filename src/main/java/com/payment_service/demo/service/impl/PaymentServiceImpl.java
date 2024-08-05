package com.payment_service.demo.service.impl;

import com.payment_service.demo.dto.PaymentDto;
import com.payment_service.demo.dto.PaymentEventDto;
import com.payment_service.demo.dto.inter_service.BookingDto;
import com.payment_service.demo.entity.PaymentEntity;
import com.payment_service.demo.exception.CustomGlobalException;
import com.payment_service.demo.feign_client.BookingClient;
import com.payment_service.demo.mapper.PaymentMapper;
import com.payment_service.demo.repository.PaymentRepository;
import com.payment_service.demo.service.KafkaPublisher;
import com.payment_service.demo.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {

    private final BookingClient bookingClient;
    private final PaymentRepository paymentRepository;
    private final KafkaPublisher kafkaPublisher;

    @Override
    @Transactional
    public PaymentDto createPayment(PaymentDto paymentDto) {
        //get booking details
        BookingDto bookingDto = bookingClient.getBookingById(paymentDto.getBookingId()).getBody();
        //validate payment amount
        validatePayment(paymentDto.getPaymentAmt(), bookingDto.getTotalAmt());
        PaymentEntity paymentEntity = paymentRepository.save(PaymentMapper.toPaymentEntity(paymentDto));

        PaymentEventDto paymentEventDto = getPaymentEventDetails(paymentDto, paymentEntity, bookingDto);

        //publish event to event service to deduct ticket count
        kafkaPublisher.sendEventToTopic("event-topic", paymentEventDto);
        //publish event to notification service

        return PaymentMapper.toPaymentDto(paymentEntity);
    }

    private static PaymentEventDto getPaymentEventDetails(PaymentDto paymentDto, PaymentEntity paymentEntity, BookingDto bookingDto) {
        PaymentEventDto paymentEventDto = new PaymentEventDto();
        paymentEventDto.setBookingId(paymentDto.getBookingId());
        paymentEventDto.setPaymentId(paymentEntity.getId());
        paymentEventDto.setEventId(bookingDto.getEventId());
        paymentEventDto.setTicketType(bookingDto.getTicketType());
        paymentEventDto.setNoOfTickets(bookingDto.getNoOfTickets());
        return paymentEventDto;
    }

    private void validatePayment(BigDecimal paymentAmt, BigDecimal totalAmt) {
        if (paymentAmt.compareTo(totalAmt) != 0) {
            throw new CustomGlobalException("AMOUNT_NOT_MATCHED", "Payment amount is not same as total amount", HttpStatus.BAD_REQUEST);
        }
    }
}
