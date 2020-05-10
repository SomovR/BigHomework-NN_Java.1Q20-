package com.application.dao;

import com.application.model.Ticket;
import com.application.model.Visitor;
import com.application.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class TicketDaoImpl implements Dao<Ticket> {
    @Override
    public void add(Ticket ticket) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(ticket);
        tx1.commit();
        session.close();
    }

    @Override
    public void edit(Ticket ticket) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(ticket);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Ticket ticket) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Visitor visitor = ticket.getVisitor();
        if (visitor.getTicket() != null) {
            visitor.setTicket(null);
        }
        session.update(visitor);
        session.delete(ticket);
        tx1.commit();
        session.close();
    }

    @Override
    public Ticket find(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        Ticket ticket = session.get(Ticket.class, id);
        tx1.commit();
        session.close();
        return ticket;
    }

    @Override
    public List<Ticket> findAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<Ticket> ticketList = session.createQuery("from Ticket ").list();
        session.close();
        return ticketList;
    }
}
