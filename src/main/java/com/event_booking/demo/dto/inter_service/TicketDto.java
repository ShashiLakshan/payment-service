package com.event_booking.demo.dto.inter_service;

import com.event_booking.demo.enums.TicketType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketDto {
    private Integer ticketId;
    private TicketType ticketType;
    private Integer noOfTickets;
    private BigDecimal unitPrice;
}
