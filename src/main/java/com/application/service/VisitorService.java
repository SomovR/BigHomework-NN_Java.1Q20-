package com.application.service;

import com.application.dao.Dao;
import com.application.model.Visitor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisitorService implements AppService<Visitor>{
    private final Dao<Visitor> visitorDao;

    public VisitorService(Dao<Visitor> visitorDao) {
        this.visitorDao = visitorDao;
    }


    @Override
    public void add(Visitor visitor) {
        this.visitorDao.add(visitor);
    }

    @Override
    public void edit(Visitor visitor) {
        this.visitorDao.edit(visitor);
    }

    @Override
    public void delete(int id) {
        this.visitorDao.delete(id);
    }

    @Override
    public Visitor find(int id) {
        return this.visitorDao.find(id);
    }

    @Override
    public List<Visitor> findAll() {
        return this.visitorDao.findAll();
    }
}
