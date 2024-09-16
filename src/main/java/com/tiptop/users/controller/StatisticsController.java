package com.tiptop.users.controller;

import com.tiptop.users.dto.TicketDTO;
import com.tiptop.users.entities.PRIZE;
import com.tiptop.users.entities.User;
import com.tiptop.users.model.TicketPrizeOut;
import com.tiptop.users.service.TicketService;
import com.tiptop.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {



    @GetMapping("/prizes")
    public PRIZE[] getPrizes(){
        return PRIZE.values();
    }


    @Autowired
    TicketService ticketService;

    @Autowired
    UserService userService;
    @GetMapping("/user/{id}")
    public Collection<TicketDTO> findTicketsByUser(@PathVariable Long id){
        User user = userService.findUserById(id);
        return ticketService.findTicketsByUser(user);
    }
    @GetMapping("/{prize}")
    public Collection<TicketPrizeOut> findTicketsByPrize(@PathVariable String prize){
        return ticketService.findTicketsByPrizeId(prize);
    }
}
