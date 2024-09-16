package com.tiptop.users.model;

import com.tiptop.users.entities.Ticket;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketPrizeOut {

    Long ticketNumber;

    Boolean isUsed;

    String prize;

    String prizeNumber;

    public TicketPrizeOut(Ticket ticket){
        this.ticketNumber = ticket.getTicketNumber();
        this.prize = ticket.getPrize();
        this.isUsed = ticket.isUsed();



    }
}
