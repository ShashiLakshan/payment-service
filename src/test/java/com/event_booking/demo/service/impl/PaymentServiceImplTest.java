package com.event_booking.demo.service.impl;

import com.event_booking.demo.dto.PaymentDto;
import com.event_booking.demo.dto.inter_service.BookingDto;
import com.event_booking.demo.dto.inter_service.EventDto;
import com.event_booking.demo.dto.inter_service.TicketDto;
import com.event_booking.demo.entity.PaymentEntity;
import com.event_booking.demo.enums.TicketType;
import com.event_booking.demo.exception.CustomGlobalException;
import com.event_booking.demo.feign_client.BookingClient;
import com.event_booking.demo.feign_client.EventClient;
import com.event_booking.demo.mapper.PaymentMapper;
import com.event_booking.demo.repository.PaymentRepository;
import com.event_booking.demo.service.KafkaPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private EventClient eventClient;

    @Mock
    private BookingClient bookingClient;

    @Mock
    private KafkaPublisher kafkaPublisher;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Captor
    private ArgumentCaptor<PaymentEntity> paymentEntityArgumentCaptor;

    private BookingDto bookingDto;
    private PaymentEntity paymentEntity;
    private EventDto eventDto;
    private TicketDto ticketDto;
    private PaymentDto paymentDto;

    @BeforeEach
    public void setUp() {
        ticketDto = TicketDto.builder()
                .ticketType(TicketType.VIP)
                .noOfTickets(10)
                .unitPrice(BigDecimal.valueOf(1000))
                .build();

        eventDto = EventDto.builder()
                .eventId(1)
                .eventName("Test Event")
                .tickets(Set.of(ticketDto))
                .build();

        bookingDto = BookingDto.builder()
                .bookingId(1)
                .eventId(1)
                .ticketType(TicketType.VIP)
                .noOfTickets(2)
                .totalAmt(BigDecimal.valueOf(2000))
                .userName("Test User")
                .build();

        paymentDto = PaymentDto.builder()
                .bookingId(1)
                .paymentAmt(BigDecimal.valueOf(2000))
                .build();

        paymentEntity = PaymentMapper.toPaymentEntity(paymentDto);
    }

    @Test
    public void whenValidPayment_thenCreatePayment() {
        when(bookingClient.getBookingById(anyInt())).thenReturn(ResponseEntity.ok(bookingDto));
        when(paymentRepository.save(any(PaymentEntity.class))).thenReturn(paymentEntity);
        when(eventClient.getEventById(anyInt())).thenReturn(ResponseEntity.ok(eventDto));

        PaymentDto createdPayment = paymentService.createPayment(paymentDto);

        assertNotNull(createdPayment);
        verify(paymentRepository).save(paymentEntityArgumentCaptor.capture());
        PaymentEntity capturedPaymentEntity = paymentEntityArgumentCaptor.getValue();

        assertEquals(paymentDto.getPaymentAmt(), capturedPaymentEntity.getPaymentAmount());
    }

    @Test
    public void whenPaymentAmountNotMatching_thenThrowException() {
        paymentDto.setPaymentAmt(BigDecimal.valueOf(1500));
        when(bookingClient.getBookingById(anyInt())).thenReturn(ResponseEntity.ok(bookingDto));

        CustomGlobalException exception = assertThrows(CustomGlobalException.class, () -> paymentService.createPayment(paymentDto));
        assertEquals("AMOUNT_NOT_MATCHED", exception.getCode());
    }

}
