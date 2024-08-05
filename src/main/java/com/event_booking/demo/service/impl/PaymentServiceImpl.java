package com.event_booking.demo.service.impl;

import com.event_booking.demo.dto.PaymentDto;
import com.event_booking.demo.dto.PaymentEventDto;
import com.event_booking.demo.entity.PaymentEntity;
import com.event_booking.demo.exception.CustomGlobalException;
import com.event_booking.demo.feign_client.BookingClient;
import com.event_booking.demo.mapper.PaymentMapper;
import com.event_booking.demo.repository.PaymentRepository;
import com.event_booking.demo.service.KafkaPublisher;
import com.event_booking.demo.service.PaymentService;
import com.event_booking.demo.dto.inter_service.BookingDto;
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
