package com.application.dao;

import com.application.model.Visitor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;
import java.util.List;

@Repository
public class VisitorDaoImpl implements Dao<Visitor>{
    private static final Logger logger = LogManager.getLogger(VisitorDaoImpl.class);
    private EntityManagerFactory entityManagerFactory;

    public VisitorDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void add(Visitor visitor) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(visitor);
        try {
            entityManager.getTransaction().commit();
            logger.info("Visitor has been added successfully " + visitor);
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            logger.error("Visitor has not been added " + visitor);
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void edit(Visitor visitor) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(visitor);
        try {
            entityManager.getTransaction().commit();
            logger.info("Visitor has been edited successfully " + visitor);
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            logger.error("Visitor has not been edited " + visitor);
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Visitor visitor;
        try {
            visitor = entityManager.createQuery("SELECT v FROM Visitor v where v.id = :id", Visitor.class).setParameter("id", id).getSingleResult();
            entityManager.remove(visitor);
            entityManager.getTransaction().commit();
            logger.info("Visitor has been deleted successfully " + visitor);
        } catch (RollbackException e) {
            entityManager.getTransaction().rollback();
            logger.error("Visitor with such id has not been found " + id);
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Visitor find(int id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Visitor visitor = entityManager.createQuery("SELECT v FROM Visitor v where v.id = :id", Visitor.class).setParameter("id", id).getSingleResult();
        logger.info("Visitor has been found successfully " + visitor);
        entityManager.close();
        return visitor;
    }

    @Override
    public List<Visitor> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Visitor> visitorList = entityManager.createQuery("from Visitor ").getResultList();
        for (Visitor visitor : visitorList) {
            logger.info("Visitor has been found successfully " + visitor);
        }
        entityManager.close();
        return visitorList;
    }
}
