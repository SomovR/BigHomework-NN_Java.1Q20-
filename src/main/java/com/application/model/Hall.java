package com.application.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Hall.class)
@Table(schema = "cinema", name = "halls")
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hall_id")
    private int id;

    @Column(name = "hall_type")
    private String hallType;

    @Column(name = "number_of_seats")
    private int numberOfSeats;

    @ManyToMany(mappedBy = "hallList", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private Set<Movie> movieList;

    public Hall() {
    }

    public Hall(String hallType, int numberOfSeats) {
        this.hallType = hallType;
        this.numberOfSeats = numberOfSeats;
        movieList = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public String getHallType() {
        return hallType;
    }

    public void setHallType(String hallType) {
        this.hallType = hallType;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Set<Movie> getMovieList() {
        return movieList;
    }

    public void setMovies(Set<Movie> moviesList) {
        this.movieList = moviesList;
    }

    public void addMovie(Movie movie) {
        movieList.add(movie);
        movie.getHallList().add(this);
    }

    @Override
    public String toString() {
        return "Hall{" +
                "id=" + id +
                ", hallType='" + hallType + '\'' +
                ", numberOfSeats=" + numberOfSeats;
    }
}