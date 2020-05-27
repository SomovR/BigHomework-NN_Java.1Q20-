package com.application.dao;

import com.application.model.Ticket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;
import java.util.List;

@Repository

public class TicketDaoImpl implements Dao<Ticket> {
    private static final Logger logger = LogManager.getLogger(TicketDaoImpl.class);
    private EntityManagerFactory entityManagerFactory;

    public TicketDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void add(Ticket ticket) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(ticket);
        try {
            entityManager.getTransaction().commit();
            logger.info("Ticket has been added successfully " + ticket);
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            logger.error("Ticket has not been added " + ticket);
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void edit(Ticket ticket) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(ticket);
        try {
            entityManager.getTransaction().commit();
            logger.info("Ticket has been edited successfully " + ticket);
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            logger.error("Ticket has not been edited " + ticket);
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Ticket ticket;
        try {
            ticket = entityManager.createQuery("SELECT t FROM Ticket t where t.id = :id", Ticket.class).setParameter("id", id).getSingleResult();
            entityManager.remove(ticket);
            entityManager.getTransaction().commit();
            logger.info("Ticket has been deleted successfully " + ticket);
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            logger.error("Ticket with such id has not been found " + id);
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Ticket find(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Ticket ticket = entityManager.createQuery("SELECT t FROM Ticket t where t.id = :id", Ticket.class).setParameter("id", id).getSingleResult();
        logger.info("Ticket has been found successfully " + ticket);
        entityManager.close();
        return ticket;
    }

    @Override
    public List<Ticket> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Ticket> ticketList = entityManager.createQuery("from Ticket").getResultList();
        for (Ticket ticket : ticketList) {
            logger.info("Ticket has been found successfully " + ticket);
        }
        entityManager.close();
        return ticketList;
    }
}
