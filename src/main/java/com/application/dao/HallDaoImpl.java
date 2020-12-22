package com.application.dao;

import com.application.model.Hall;
import com.application.model.Movie;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;
import java.util.List;

@Repository
public class HallDaoImpl implements Dao<Hall> {
    private static final Logger logger = LogManager.getLogger(HallDaoImpl.class);

    private EntityManagerFactory entityManagerFactory;

    public HallDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void add(Hall hall) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(hall);
        try {
            entityManager.getTransaction().commit();
            logger.info("Hall has been added successfully " + hall);
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            logger.error("Hall has not been added " + hall);
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void edit(Hall hall) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(hall);
        try {
            entityManager.getTransaction().commit();
            logger.info("Hall has been edited successfully " + hall);
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            logger.error("Hall has not been edited " + hall);
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Hall hall;
        try {
            hall = entityManager.createQuery("SELECT h FROM Hall h where h.id = :id", Hall.class).setParameter("id", id).getSingleResult();
            for (Movie movie : hall.getMovieList()) {
                movie.getHallList().remove(hall);
                hall.getMovieList().remove(movie);
                entityManager.merge(movie);
                entityManager.merge(hall);
            }
            entityManager.remove(hall);
            entityManager.getTransaction().commit();
            logger.info("Hall has been deleted successfully " + hall);
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            logger.error("Hall with such id has not been found " + id);
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Hall find(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Hall hall = entityManager.createQuery("SELECT h FROM Hall h where h.id = :id", Hall.class).setParameter("id", id).getSingleResult();
        logger.info("Hall has been found successfully " + hall);
        entityManager.close();
        return hall;
    }

    @Override
    public List<Hall> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Hall> hallList = entityManager.createQuery("from Hall").getResultList();
        for (Hall hall : hallList) {
            logger.info("Hall has been found successfully " + hall);
        }
        entityManager.close();
        return hallList;
    }
}
