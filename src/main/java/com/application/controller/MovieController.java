package com.application.controller;

import com.application.dao.Dao;
import com.application.model.Movie;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/movies")
public class MovieController {
    private Dao<Movie> movieDao;

    public MovieController(Dao<Movie> movieDao) {
        this.movieDao = movieDao;
    }

    @GetMapping(path = "/{id}")
    public Movie find(@PathVariable(name = "id") int id) {
        return movieDao.find(id);
    }

    @GetMapping(path = "/all")
    public List<Movie> findAll() {
        return movieDao.findAll();
    }

    @PostMapping(path = "/add")
    public void add(@RequestBody Movie movie) {
        movieDao.add(movie);
    }

    @PutMapping(path = "/edit")
    public void edit(@RequestBody Movie movie) {
        movieDao.edit(movie);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void delete(@PathVariable(name = "id") int id) {
        movieDao.delete(id);
    }
}
