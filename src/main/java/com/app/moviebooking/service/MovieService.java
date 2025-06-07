package com.app.moviebooking.service;

import com.app.moviebooking.model.Movie;
import com.app.moviebooking.model.Schedule;
import com.app.moviebooking.model.Seat;

import java.util.List;

public interface MovieService {
    List<Movie> getAllMovies();
    Movie getMovieById(Long id);
    List<Seat> getSeatById(Long id, String date);
    List<Schedule> getScheduleById(Long id);
}