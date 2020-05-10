package com.application.dao;

import com.application.model.Hall;
import com.application.model.Movie;
import com.application.model.Ticket;
import com.application.model.Visitor;
import com.application.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Set;

public class MovieDaoImpl implements Dao<Movie>{
    @Override
    public void add(Movie movie) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(movie);
        tx1.commit();
        session.close();
    }

    @Override
    public void edit(Movie movie) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(movie);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Movie movie) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        for (Hall hall : movie.getHallList()) {
            movie.getHallList().remove(hall);
            hall.getMovieList().remove(movie);
            session.update(movie);
            session.update(hall);
        }
        Set<Ticket> tickets = movie.getTicketList();
        for (Ticket ticket : tickets) {
            Visitor visitor = ticket.getVisitor();
            if (visitor.getTicket() != null) {
                visitor.setTicket(null);
            }
            session.update(visitor);
        }
        session.delete(movie);
        tx1.commit();
        session.close();
    }

    @Override
    public Movie find(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Movie movie = session.get(Movie.class, id);
        session.close();
        return movie;
    }

    @Override
    public List<Movie> findAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<Movie> movieList = session.createQuery("from Movie").list();
        session.close();
        return movieList;
    }
}
