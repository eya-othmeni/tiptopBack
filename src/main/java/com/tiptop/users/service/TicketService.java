package com.tiptop.users.service;

import java.util.*;
import java.util.stream.Collectors;

import com.tiptop.users.dto.PrizeDTO;
import com.tiptop.users.dto.TicketDTO;
import com.tiptop.users.entities.PRIZE;
import com.tiptop.users.entities.Ticket;
import com.tiptop.users.entities.User;
import com.tiptop.users.model.TicketPrizeOut;
import com.tiptop.users.repos.ITicketRepository;
import com.tiptop.users.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;



@Service
public class TicketService implements ITicketService{
	
	@Autowired
	ITicketRepository ticketRepo;

	@Autowired
	UserRepository userRepo;

	@Override
	public Long getMaxNumOfTickets() {
		return ticketRepo.count();
	}

	public List<TicketDTO> findTicketsByUser(User user) {
		return this.ticketRepo.findByUser(user).stream().map(ticket -> new TicketDTO(ticket)).collect(Collectors.toList());
	}

	public boolean verifyNewTicket(Long ticketNumber){
		Ticket ticket = ticketRepo.findByTicketNumber(ticketNumber);
		return ticket == null;
	}
	
	@Override
	public ResponseEntity<String> addTicket(Long ticketNumber) throws RuntimeException{
		if (getMaxNumOfTickets() > 15000) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("nous avons atteint le nombre maximale des participations");
		}
		if (!verifyNewTicket(ticketNumber)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ce numéro de ticket est déjà utilisé");
		}
		if ( ticketNumber.toString().length() != 10){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		if ((getMaxNumOfTickets() < 150000) && (verifyNewTicket(ticketNumber))&& (ticketNumber.toString().length() == 10)) {
			Ticket ticket= new Ticket();
			ticket.setTicketNumber(ticketNumber);
			ticketRepo.save(ticket);

		}
		return ResponseEntity.ok("Ticket ajouté avec succés");
	}


	@Override
	public List<Ticket> getAllTickets(){
		return ticketRepo.findAll();
	}

	public Ticket updateTicketStatus(Long ticketNumber){
		 Ticket ticket= ticketRepo.findByTicketNumber(ticketNumber);
		 ticket.setStatus("reclame");
		 return ticketRepo.save(ticket);
	}

	public Ticket asigneTicketToUser(Long ticketNumber,Long userId){
		//test if its not taken
		Ticket ticket = ticketRepo.findByTicketNumber(ticketNumber);
		User user = userRepo.findById(userId).orElse(null);
		if (ticket.getPrize()== null) {
			ticket.setUser(user);
			ticket.setPrize(assignPrizeToTicket(ticketNumber));
			ticketRepo.save(ticket);
		}
		else
			ticket = null;
		return ticket;
	}

	public List<TicketPrizeOut> getTicketByUserId(Long userId){
		Collection<Ticket> tickets =  ticketRepo.findTicketsByUserId(userId);
		return tickets.stream().map(ticket -> new TicketPrizeOut(ticket)).collect(Collectors.toList());
	}

	public List<Object[]> findAllTicketsWithUsers(){
		return ticketRepo.findAllTicketsWithUsers();
	}

	public String assignPrizeToTicket(Long ticketNumber){
		Random random = new Random();
		int randomNumber = random.nextInt(100) + 1; // Generate a random number between 1 and 100

		if (randomNumber <= 60) {
			return "Infuseur";
		} else if (randomNumber <= 80) {
			return "Boite de 100g d’un thé détox ou d’infusion";
		} else if (randomNumber <= 90) {
			return "Boite de 100g d’un thé signature";
		} else if (randomNumber <= 96) {
			return "Coffret découverte d’une valeur de 39€";
		} else {
			return "Coffret découverte d’une valeur de 69€";
		}
	}

	public Collection<TicketDTO> findAllTickets(){
		Collection<Ticket> tickets = this.ticketRepo.findAll();
		return tickets.stream().map(ticket -> new TicketDTO(ticket)).collect(Collectors.toList());
	}
	public Collection<TicketDTO> findUserTickets(User user){
		Collection<Ticket> tickets = this.ticketRepo.findAll();
		return tickets.stream().map(ticket -> new TicketDTO(ticket)).collect(Collectors.toList());
	}

	public TicketDTO findTicketByNumber(Long id){
		Ticket ticket = ticketRepo.findByTicketNumber(id);
		return new TicketDTO(ticket);
	}

	public PrizeDTO spinWheel(Ticket ticket, User user) {
		List<PRIZE> items = new ArrayList<>();

		for (PRIZE item : PRIZE.values())
			for (int i = 0; i < item.percentage; i++)
				items.add(item);

		Collections.shuffle(items);
		PRIZE[] prizes = (PRIZE[]) items.toArray(new PRIZE[]{});

		Random random = new Random();

		// Generate a random number between 0 (inclusive) and 100 (exclusive)
		int randomNumber = random.nextInt(100);

		PRIZE prizeWon = prizes[randomNumber];
		user.getPrizes().add(prizeWon);
		ticket.setUsed(true);
		ticket.setPrize(prizeWon.prize);
		ticketRepo.save(ticket);
		int angle = 0;
		switch (prizeWon){
			case P1: angle = getRange(0,72);break;
			case P2: angle = getRange(73,145);break;
			case P3: angle = getRange(146,218);break;
			case P4: angle = getRange(219,291);break;
			case P5: angle = getRange(286,360);break;
		}

		angle+=8*360;
		return new PrizeDTO(prizeWon,user,angle);




	}


	public Ticket persistTicket(Ticket ticket){
		this.ticketRepo.save(ticket);
		return ticket;
	}

	private int getRange(int min , int max){
		// Validate the range
		if (min > max) {
			throw new IllegalArgumentException("Invalid range: min should be less than or equal to max");
		}

		// Calculate the range (inclusive)
		int range = max - min + 1;

		// Generate and return a random number within the range
		return (int) (Math.random() * range) + min;
	}

	public Collection<TicketPrizeOut> findTicketsByPrizeId(String prize){
		PRIZE p = null;
		if (prize.equals("P1")) {
			p = PRIZE.P1;
		}
		if (prize.equals("P2")) {
			p = PRIZE.P2;
		}
		if (prize.equals("P3")) {
			p = PRIZE.P3;
		}
		if (prize.equals("P4")) {
			p = PRIZE.P4;
		}
		if (prize.equals("P5")) {
			p = PRIZE.P5;
		}

		Collection<Ticket> tickets = this.ticketRepo.findTicketByPrize(p.prize);
		return tickets.stream().map(ticket -> new TicketPrizeOut(ticket)).collect(Collectors.toList());
	}


}
