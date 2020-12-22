package com.application.dao;

import com.application.model.Hall;
import com.application.model.Movie;
import com.application.model.Ticket;
import com.application.model.Visitor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;
import java.util.List;
import java.util.Set;

@Repository
public class MovieDaoImpl implements Dao<Movie>{
    private static final Logger logger = LogManager.getLogger(MovieDaoImpl.class);
    private EntityManagerFactory entityManagerFactory;

    public MovieDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void add(Movie movie) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(movie);
        try {
            entityManager.getTransaction().commit();
            logger.info("Movie has been added successfully " + movie);
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            logger.error("Movie has not been added " + movie);
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void edit(Movie movie) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(movie);
        try {
            entityManager.getTransaction().commit();
            logger.info("Movie has been edited successfully " + movie);
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            logger.error("Movie has not been edited " + movie);
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Movie movie;
        try {
            movie = entityManager.createQuery("SELECT m FROM Movie m where m.id = :id", Movie.class).setParameter("id", id).getSingleResult();
            for (Hall hall : movie.getHallList()) {
                movie.getHallList().remove(hall);
                hall.getMovieList().remove(movie);
                entityManager.merge(movie);
                entityManager.merge(hall);
            }
            Set<Ticket> tickets = movie.getTicketList();
            for (Ticket ticket : tickets) {
                Visitor visitor = ticket.getVisitor();
                if (visitor.getTicket() != null) {
                    visitor.setTicket(null);
                }
                entityManager.merge(visitor);
            }
            entityManager.remove(movie);
            entityManager.getTransaction().commit();
            logger.info("Movie has been deleted successfully " + movie);
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            logger.error("Movie with such id has not been found " + id);
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Movie find(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Movie movie = entityManager.createQuery("SELECT m FROM Movie m where m.id = :id", Movie.class).setParameter("id", id).getSingleResult();
        logger.info("Movie has been found successfully " + movie);
        entityManager.close();
        return movie;
    }

    @Override
    public List<Movie> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Movie> movieList = entityManager.createQuery("from Movie ").getResultList();
        for (Movie movie : movieList) {
            logger.info("Movie has been found successfully " + movie);
        }
        entityManager.close();
        return movieList;
    }
}
