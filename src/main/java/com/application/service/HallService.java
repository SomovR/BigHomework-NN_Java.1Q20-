package com.application.service;

import com.application.dao.HallDaoImpl;
import com.application.model.Hall;

import java.util.List;

public class HallService {
    private HallDaoImpl hallDao = new HallDaoImpl();

    public void setHallDao(HallDaoImpl hallDao) {
        this.hallDao = hallDao;
    }

    public void addHall(Hall hall) {
        this.hallDao.add(hall);
    }

    public void editHall(Hall hall) {
        this.hallDao.edit(hall);
    }

    public void deleteHall(Hall hall) {
        this.hallDao.delete(hall);
    }

    public Hall findHallById(int id) {
        return this.hallDao.find(id);
    }

    public List<Hall> findHalls() {
        return this.hallDao.findAll();
    }

}
