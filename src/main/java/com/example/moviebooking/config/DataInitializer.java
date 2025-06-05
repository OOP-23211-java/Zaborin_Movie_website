package com.example.moviebooking.config;

import com.example.moviebooking.model.Movie;
import com.example.moviebooking.model.Schedule;
import com.example.moviebooking.model.Seat;
import com.example.moviebooking.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initMovies(MovieRepository movieRepository) {

        return args -> {
            movieRepository.save(createMovie("kojan.jpg", "ДГМА", "Психологический хоррор, триллер", "...", "10"));
            movieRepository.save(createMovie("START.jpg", "Начало", "ХУЙНЯ", "Кино про сны внутри снов", "21"));
            movieRepository.save(createMovie("START.jpg", "Начало", "ХУЙНЯ", "Кино про сны внутри снов", "12"));

        };

//        return args -> {
//
//            movieRepository.save(new Movie( "Начало", "Кино про сны внутри снов", Seat.getStart()));
//            movieRepository.save(new Movie( "Матрица", "Фантастический боевик",Seat.getStart() ));
//            movieRepository.save(new Movie( "Интерстеллар", "Космическая эпопея", Seat.getStart()));
//        };
    }
    private Movie createMovie(String imgSrc, String title, String genre, String description, String date) {
        Movie movie = new Movie(imgSrc,title, genre, description, null);


        List<Schedule> schedules = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            Schedule sh = ScheduleInitializer.createScheduleWithEmptySeats(movie, date);
            schedules.add(sh);
        }
        movie.setSchedules(schedules);
        return movie;
    }
}