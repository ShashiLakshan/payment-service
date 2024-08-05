package com.payment_service.demo.dto.inter_service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.payment_service.demo.enums.TicketType;
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
@JsonRootName(value = "Booking")
public class BookingDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer bookingId;
    private Integer eventId;
    private String userName;
    private TicketType ticketType;
    private Integer noOfTickets;
    private BigDecimal totalAmt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingDto that = (BookingDto) o;
        return Objects.equals(bookingId, that.bookingId) &&
                Objects.equals(eventId, that.eventId) && Objects.equals(userName, that.userName)
                && ticketType == that.ticketType && Objects.equals(noOfTickets, that.noOfTickets)
                && Objects.equals(totalAmt, that.totalAmt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, eventId, userName, ticketType, noOfTickets, totalAmt);
    }
}
