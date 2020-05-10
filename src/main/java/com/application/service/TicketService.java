package com.application.service;

import com.application.dao.TicketDaoImpl;
import com.application.model.Ticket;

import java.util.List;

public class TicketService {
    private TicketDaoImpl ticketDao = new TicketDaoImpl();

    public void setTicketDao(TicketDaoImpl ticketDao) {
        this.ticketDao = ticketDao;
    }

    public void addTicket(Ticket ticket) {
        this.ticketDao.add(ticket);
    }

    public void editTicket(Ticket ticket) {
        this.ticketDao.edit(ticket);
    }

    public void deleteTicket(Ticket ticket) {
        this.ticketDao.delete(ticket);
    }

    public Ticket findTicketById(int id) {
        return this.ticketDao.find(id);
    }

    public List<Ticket> findTickets() {
        return this.ticketDao.findAll();
    }
}
