package com.jls.example.web;

import com.jls.example.dto.OrderDTO;
import com.jls.example.dao.TicketRepository;
import com.jls.example.domain.Ticket;
import com.jls.example.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/ticket")
public class TicketResource {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketService ticketService;

    @PostConstruct
    public void init() {
        if (ticketRepository.count() > 0) {
            return;
        }
        Ticket ticket = new Ticket();
        ticket.setName("Num.1");
        ticket.setTicketNum(100L);
        ticketRepository.save(ticket);
    }

    @PostMapping("/lock")
    public Ticket lock(@RequestBody OrderDTO dto) {
        return ticketService.ticketLock(dto);
    }

    @PostMapping("/lock2")
    public int lock2(@RequestBody OrderDTO dto) {
        return ticketService.ticketLock2(dto);
    }
}