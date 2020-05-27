package com.application.controller;

import com.application.dao.Dao;
import com.application.model.Visitor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/visitors")
public class VisitorController {
    private Dao<Visitor> visitorDao;

    public VisitorController(Dao<Visitor> visitorDao) {
        this.visitorDao = visitorDao;
    }

    @GetMapping(path = "/{id}")
    public Visitor find(@PathVariable(name = "id") int id) {
        return visitorDao.find(id);
    }

    @GetMapping(path = "/all")
    public List<Visitor> findAll() {
        return visitorDao.findAll();
    }

    @PostMapping(path = "/add")
    public void add(@RequestBody Visitor visitor) {
        visitorDao.add(visitor);
    }

    @PutMapping(path = "/edit")
    public void edit(@RequestBody Visitor visitor) {
        visitorDao.edit(visitor);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void delete(@PathVariable(name = "id") int id) {
        visitorDao.delete(id);
    }
}
