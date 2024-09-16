package com.tiptop.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tiptop.users.entities.ROLES;
import com.tiptop.users.entities.Ticket;
import com.tiptop.users.entities.User;
import lombok.*;

import java.util.Collection;
import java.util.stream.Collectors;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class UserDTO {
    private Boolean enabled;
    private String email;
    private String username;
    private String role;
    private Collection<TicketDTO> tickets;
    private Long id;

    public UserDTO() {

    }

    public UserDTO(User user) {
        this.id = user.getUser_id();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.role = String.valueOf(user.getRole());
        this.enabled = user.getEnabled();
        Collection<Ticket> tickets = user.getTickets();
        initializeTickets(tickets);
    }

    private void initializeTickets(Collection<Ticket> tickets){
        this.tickets = tickets.stream().map(
                ticket -> {
                    TicketDTO ticketDTO = new TicketDTO();
                    ticketDTO.setTicketNumber(ticket.getTicketNumber());
                    ticketDTO.setActive(ticket.isUsed());
                    return ticketDTO;
                }).collect(Collectors.toList());
    }
}
