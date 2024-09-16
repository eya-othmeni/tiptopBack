package com.tiptop.users.dto;

import com.tiptop.users.entities.Ticket;
import com.tiptop.users.entities.User;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class TicketDTO {

    private Long ticketNumber;

    private UserDTO user;

    boolean active;
    public TicketDTO() {
    }

    public TicketDTO(Ticket ticket){
        this.setUser(new UserDTO(ticket.getUser()));
        this.active=ticket.isUsed();
        this.ticketNumber = ticket.getTicketNumber();

    }
}
