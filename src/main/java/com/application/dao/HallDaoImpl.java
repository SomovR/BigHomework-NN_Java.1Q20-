package com.application.dao;

import com.application.model.Hall;
import com.application.model.Movie;
import com.application.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HallDaoImpl implements Dao<Hall> {
    @Override
    public void add(Hall hall) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(hall);
        tx1.commit();
        session.close();
    }

    @Override
    public void edit(Hall hall) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(hall);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Hall hall) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        for (Movie movie : hall.getMovieList()) {
            movie.getHallList().remove(hall);
            hall.getMovieList().remove(movie);
            session.update(movie);
            session.update(hall);
        }
        session.delete(hall);
        tx1.commit();
        session.close();
    }

    @Override
    public Hall find(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Hall hall = session.get(Hall.class, id);
        session.close();
        return hall;
    }

    @Override
    public List<Hall> findAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<Hall> hallList = session.createQuery("from Hall").list();
        session.close();
        return hallList;
    }
}
