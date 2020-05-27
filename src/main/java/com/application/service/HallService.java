package com.application.service;

import com.application.dao.Dao;
import com.application.model.Hall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallService implements AppService<Hall> {
    private final Dao<Hall> hallDao;

    public HallService(Dao<Hall> hallDao) {
        this.hallDao = hallDao;
    }

    @Override
    public void add(Hall hall) {
        this.hallDao.add(hall);
    }

    @Override
    public void edit(Hall hall) {
        this.hallDao.edit(hall);
    }

    @Override
    public void delete(int id) {
        this.hallDao.delete(id);
    }

    @Override
    public Hall find(int id) {
        return this.hallDao.find(id);
    }

    @Override
    public List<Hall> findAll() {
        return this.hallDao.findAll();
    }
}