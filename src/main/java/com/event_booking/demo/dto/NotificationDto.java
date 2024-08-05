package com.event_booking.demo.dto;

import com.event_booking.demo.enums.NotificationStatus;
import com.event_booking.demo.enums.TicketType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Data
public class NotificationDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String eventName;
    private Integer bookingId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    private String eventLocation;
    private String userName;
    private TicketType ticketType;
    private Integer noOfTickets;
    private BigDecimal payAmount;
    private NotificationStatus notificationStatus;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationDto that = (NotificationDto) o;
        return Objects.equals(eventName, that.eventName) && Objects.equals(eventDate, that.eventDate) && Objects.equals(eventLocation, that.eventLocation)
                && Objects.equals(userName, that.userName) && ticketType == that.ticketType && Objects.equals(noOfTickets, that.noOfTickets) && Objects.equals(payAmount, that.payAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName, eventDate, eventLocation, userName, ticketType, noOfTickets, payAmount);
    }
}
