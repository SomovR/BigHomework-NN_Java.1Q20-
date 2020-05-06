package com.application.service;

import com.application.dao.MovieDaoImpl;
import com.application.model.Movie;

import java.util.List;

public class MovieService {
    private MovieDaoImpl movieDao = new MovieDaoImpl();

    public void setMovieDao(MovieDaoImpl movieDao) {
        this.movieDao = movieDao;
    }

    public void addMovie(Movie movie) {
        this.movieDao.add(movie);
    }

    public void editMovie(Movie movie) {
        this.movieDao.edit(movie);
    }

    public void deleteMovie(Movie movie) {
        this.movieDao.delete(movie);
    }

    public Movie findMovieById(int id) {
        return this.movieDao.find(id);
    }

    public List<Movie> findMovies() {
        return this.movieDao.findAll();
    }
}
