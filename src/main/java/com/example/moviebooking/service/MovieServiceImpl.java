package com.example.moviebooking.service;

import com.example.moviebooking.model.Movie;
import com.example.moviebooking.model.Schedule;
import com.example.moviebooking.model.Seat;
import com.example.moviebooking.repository.MovieRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @Override
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
    }
    @Override
    public List<Schedule> getScheduleById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        return movie.getSchedules();
    }
    @Override
    public List<Seat> getSeatById(Long id, String date) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        List<Schedule> schedules = movie.getSchedules();
        Schedule schedule = schedules.get(0);
        for (Schedule sh : schedules) {
            System.out.println(sh.getDate());
            if (sh.getDate().equals(date)){
                schedule = sh;
                break;
            }
        }

        return schedule.getSeats();
    }
}