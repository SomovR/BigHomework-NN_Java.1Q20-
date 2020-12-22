package com.application.service;

import com.application.dao.Dao;
import com.application.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService implements AppService<Ticket> {
    private final Dao<Ticket> ticketDao;

    public TicketService(Dao<Ticket> ticketDao) {
        this.ticketDao = ticketDao;
    }

    @Override
    public void add(Ticket ticket) {
        this.ticketDao.add(ticket);
    }

    @Override
    public void edit(Ticket ticket) {
        this.ticketDao.edit(ticket);
    }

    @Override
    public void delete(int id) {
        this.ticketDao.delete(id);
    }

    @Override
    public Ticket find(int id) {
        return this.ticketDao.find(id);
    }

    @Override
    public List<Ticket> findAll() {
        return this.ticketDao.findAll();
    }
}
