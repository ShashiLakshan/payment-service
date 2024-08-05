package com.event_booking.demo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.event_booking.demo.marker_interfaces.CreateMarker;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "Payment")
public class PaymentDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Null(groups = CreateMarker.class)
    private Integer paymentId;

    @NotNull(groups = CreateMarker.class)
    private Integer bookingId;

    @NotNull(groups = CreateMarker.class)
    private BigDecimal paymentAmt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentDto that = (PaymentDto) o;
        return Objects.equals(paymentId, that.paymentId) && Objects.equals(bookingId, that.bookingId) && Objects.equals(paymentAmt, that.paymentAmt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, bookingId, paymentAmt);
    }
}
