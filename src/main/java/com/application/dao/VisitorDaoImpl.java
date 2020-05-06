package com.application.dao;

import com.application.model.Visitor;
import com.application.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class VisitorDaoImpl implements Dao<Visitor>{
    @Override
    public void add(Visitor visitor) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(visitor);
        tx1.commit();
        session.close();
    }

    @Override
    public void edit(Visitor visitor) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(visitor);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(Visitor visitor) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(visitor);
        tx1.commit();
        session.close();
    }

    @Override
    public Visitor find(int id) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Visitor visitor = session.get(Visitor.class, id);
        session.close();
        return visitor;
    }

    @Override
    public List<Visitor> findAll() {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        List<Visitor> visitorList = session.createQuery("from Visitor ").list();
        session.close();
        return visitorList;
    }
}
