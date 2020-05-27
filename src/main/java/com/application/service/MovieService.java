package com.application.service;

import com.application.dao.Dao;
import com.application.model.Movie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService implements AppService<Movie> {
    private final Dao<Movie> movieDao;

    public MovieService(Dao<Movie> movieDao) {
        this.movieDao = movieDao;
    }

    @Override
    public void add(Movie movie) {
        this.movieDao.add(movie);
    }

    @Override
    public void edit(Movie movie) {
        this.movieDao.edit(movie);
    }

    @Override
    public void delete(int id) {
        this.movieDao.delete(id);
    }

    @Override
    public Movie find(int id) {
        return this.movieDao.find(id);
    }

    @Override
    public List<Movie> findAll() {
        return this.movieDao.findAll();
    }
}
