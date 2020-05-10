package com.application.service;

import com.application.dao.VisitorDaoImpl;
import com.application.model.Visitor;

import java.util.List;

public class VisitorService {
    private VisitorDaoImpl visitorDao = new VisitorDaoImpl();

    public void setVisitorDao(VisitorDaoImpl visitorDao) {
        this.visitorDao = visitorDao;
    }

    public void addVisitor(Visitor visitor) {
        this.visitorDao.add(visitor);
    }

    public void editVisitor(Visitor visitor) {
        this.visitorDao.edit(visitor);
    }

    public void deleteVisitor(Visitor visitor) {
        this.visitorDao.delete(visitor);
    }

    public Visitor findVisitorById(int id) {
        return this.visitorDao.find(id);
    }

    public List<Visitor> findVisitors() {
        return this.visitorDao.findAll();
    }
}
