package com.application.controller;

import com.application.dao.Dao;
import com.application.model.Ticket;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/tickets")
public class TicketController {
    private Dao<Ticket> ticketDao;

    public TicketController(Dao<Ticket> ticketDao) {
        this.ticketDao = ticketDao;
    }

    @GetMapping(path = "/{id}")
    public Ticket find(@PathVariable(name = "id") int id) {
        return ticketDao.find(id);
    }

    @GetMapping(path = "/all")
    public List<Ticket> findAll() {
        return ticketDao.findAll();
    }

    @PostMapping(path = "/add")
    public void add(@RequestBody Ticket ticket) {
        ticketDao.add(ticket);
    }

    @PutMapping(path = "/edit")
    public void edit(@RequestBody Ticket ticket) {
        ticketDao.edit(ticket);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void delete(@PathVariable(name = "id") int id) {
        ticketDao.delete(id);
    }
}
