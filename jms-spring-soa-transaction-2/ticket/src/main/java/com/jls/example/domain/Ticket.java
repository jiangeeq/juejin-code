package com.jls.example.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Long owner;
    private Long lockUser;
    private Long ticketNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }

    public Long getLockUser() {
        return lockUser;
    }

    public void setLockUser(Long lockUser) {
        this.lockUser = lockUser;
    }

    public Long getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(Long ticketNum) {
        this.ticketNum = ticketNum;
    }
}
