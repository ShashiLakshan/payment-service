package com.event_booking.demo.mapper;

import com.event_booking.demo.dto.PaymentDto;
import com.event_booking.demo.entity.PaymentEntity;

public class PaymentMapper {

    private PaymentMapper() {
    }

    public static PaymentDto toPaymentDto(PaymentEntity entity) {
        return PaymentDto.builder()
                .paymentId(entity.getId())
                .paymentAmt(entity.getPaymentAmount())
                .bookingId(entity.getBookingId())
                .build();
    }

    public static PaymentEntity toPaymentEntity(PaymentDto dto) {
        PaymentEntity.PaymentEntityBuilder builder = PaymentEntity.builder()
                .paymentAmount(dto.getPaymentAmt())
                .bookingId(dto.getBookingId());

        if (dto.getPaymentId() != null) {
            builder.id(dto.getPaymentId());
        }
        return builder.build();
    }
}
