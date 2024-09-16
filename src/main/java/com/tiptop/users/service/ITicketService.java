package com.tiptop.users.service;

import com.tiptop.users.dto.TicketDTO;
import com.tiptop.users.entities.Ticket;
import com.tiptop.users.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;

public interface ITicketService {
	
	Long getMaxNumOfTickets();
	ResponseEntity<String> addTicket(Long ticket);

	List<Ticket> getAllTickets();

	public Collection<TicketDTO> findAllTickets();

	public List<TicketDTO> findTicketsByUser(User user);
}
