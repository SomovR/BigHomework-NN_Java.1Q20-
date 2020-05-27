package com.application.controller;

import com.application.dao.Dao;
import com.application.model.Hall;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/halls")
public class HallController {
    private Dao<Hall> hallDao;

    public HallController(Dao<Hall> hallDao) {
        this.hallDao = hallDao;
    }

    @GetMapping(path = "/{id}")
    public Hall find(@PathVariable(name = "id") int id) {
        return hallDao.find(id);
    }

    @GetMapping(path = "/all")
    public List<Hall> findAll() {
        return hallDao.findAll();
    }

    @PostMapping(path = "/add")
    public void add(@RequestBody Hall hall) {
        hallDao.add(hall);
    }

    @PutMapping(path = "/edit")
    public void edit(@RequestBody Hall hall) {
        hallDao.edit(hall);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void delete(@PathVariable(name = "id") int id) {
        hallDao.delete(id);
    }
}
