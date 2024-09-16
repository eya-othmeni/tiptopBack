package com.tiptop.users.controller;

import com.tiptop.users.dto.PrizeDTO;
import com.tiptop.users.dto.TicketDTO;
import com.tiptop.users.entities.Ticket;
import com.tiptop.users.entities.User;
import com.tiptop.users.exception.NoTicketFoundException;
import com.tiptop.users.model.CreateTicketIn;
import com.tiptop.users.model.TicketPrizeOut;
import com.tiptop.users.model.TicketCountOut;
import com.tiptop.users.service.TicketService;
import com.tiptop.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/tickets")
public class TicketController {

	@Autowired
	TicketService ticketService;

	@Autowired
	UserService userService;
	@GetMapping("")
	public Collection<TicketDTO> findAllTickets(){
		return ticketService.findAllTickets();
	}
	@GetMapping("/me")
	public Collection<TicketDTO> findMyTickets(@AuthenticationPrincipal Object principal) {
		User user = userService.findUserByUsername(principal.toString());
		return ticketService.findTicketsByUser(user);
	}
	@GetMapping("/{id}")
	public Collection<TicketDTO> findMyTickets(@PathVariable Long id) {
		User user = userService.findUserById(id);
		return ticketService.findTicketsByUser(user);
	}

	@GetMapping("/count")
	public TicketCountOut getTicketCount(@AuthenticationPrincipal Object principal) {
		User user = userService.findUserByUsername(principal.toString());
		long count = user.getTickets().stream().count();
		return new TicketCountOut(count);
	}
	@PostMapping("/checkTicket/{id}")
	public Boolean checkTicket(@PathVariable Long id , @AuthenticationPrincipal Object principal) {
		TicketDTO ticket = this.ticketService.findTicketByNumber(id);
		return ticket.isActive();
	}

	@PostMapping
	public TicketDTO addTicketToUser(@RequestBody CreateTicketIn createTicketIn){
		Ticket ticket = new Ticket();
		User user = userService.findUserById(createTicketIn.getUserId());
		ticket.setUsed(false);
		ticket.setUser(user);
		this.ticketService.persistTicket(ticket);
		TicketDTO ticketDTO = new TicketDTO(ticket);
		return ticketDTO;
	}



	@PostMapping("/addTicket")
	public ResponseEntity<String> addTicket(@RequestBody Long ticket) {
		 return ticketService.addTicket(ticket);
	}


	@GetMapping("/getTickets")
	public List<Ticket> getTickets(){
		return ticketService.getAllTickets();
	}

	@PutMapping("/updateTicket/{ticketNumber}")
	public Ticket updateTicketStatus(@PathVariable Long ticketNumber){
		return ticketService.updateTicketStatus(ticketNumber);
	}
	@PutMapping("/asignTicket/{userId}")
	public Ticket asigneTicketToUser(@RequestBody Long ticketNumber,@PathVariable Long userId){
		return ticketService.asigneTicketToUser(ticketNumber,userId);
	}

	@GetMapping("/getTicketByUserId/{userId}")
	public List<TicketPrizeOut> getTicketByUserId(@PathVariable Long userId){
		return ticketService.getTicketByUserId(userId);
	}
	@GetMapping("/getAllTickets")
	public List<Object[]> findAllTicketsWithUsers(){
		return ticketService.findAllTicketsWithUsers();
	}

	@PostMapping("/play/{ticketNumber}")
	public PrizeDTO spinwheel(@PathVariable Long ticketNumber, @AuthenticationPrincipal Object principal) {
		User user = userService.findUserByUsername(principal.toString());
		Collection<Ticket> tickets = user.getTickets();
		Optional<Ticket> ticketOptional = tickets.stream().filter(ticket -> ticket.getTicketNumber().equals(ticketNumber)).findAny();
		Ticket ticket = ticketOptional.orElseThrow(() -> new NoTicketFoundException("new ticket found for user " + user.getUsername()));
		PrizeDTO prize = ticketService.spinWheel(ticket, user);
		return prize;
	 }



}
