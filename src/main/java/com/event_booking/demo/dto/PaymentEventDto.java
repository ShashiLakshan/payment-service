package com.event_booking.demo.dto;

import com.event_booking.demo.enums.TicketType;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Data
public class PaymentEventDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer paymentId;
    private Integer bookingId;
    private Integer eventId;
    private TicketType ticketType;
    private Integer noOfTickets;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentEventDto that = (PaymentEventDto) o;
        return Objects.equals(paymentId, that.paymentId) && Objects.equals(bookingId, that.bookingId) && Objects.equals(eventId, that.eventId) && ticketType == that.ticketType && Objects.equals(noOfTickets, that.noOfTickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, bookingId, eventId, ticketType, noOfTickets);
    }
}
