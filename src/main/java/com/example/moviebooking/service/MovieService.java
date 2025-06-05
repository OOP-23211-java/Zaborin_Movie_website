package com.example.moviebooking.service;

import com.example.moviebooking.model.Movie;
import com.example.moviebooking.model.Seat;

import java.util.List;

public interface MovieService {
    List<Movie> getAllMovies();
    Movie getMovieById(Long id);
    List<Seat> getSeatById(Long id);
}