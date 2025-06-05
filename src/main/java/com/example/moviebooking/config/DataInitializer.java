package com.example.moviebooking.config;

import com.example.moviebooking.model.Movie;
import com.example.moviebooking.model.Seat;
import com.example.moviebooking.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initMovies(MovieRepository movieRepository) {

        return args -> {
            movieRepository.save(createMovie("kojan.jpg", "ДГМА", "Психологический хоррор, триллер", "..."));
            movieRepository.save(createMovie("START.jpg", "Начало", "ХУЙНЯ", "Кино про сны внутри снов"));
            movieRepository.save(createMovie("START.jpg", "Начало", "ХУЙНЯ", "Кино про сны внутри снов"));

        };

//        return args -> {
//
//            movieRepository.save(new Movie( "Начало", "Кино про сны внутри снов", Seat.getStart()));
//            movieRepository.save(new Movie( "Матрица", "Фантастический боевик",Seat.getStart() ));
//            movieRepository.save(new Movie( "Интерстеллар", "Космическая эпопея", Seat.getStart()));
//        };
    }
    private Movie createMovie(String imgSrc, String title, String genre, String description) {
        Movie movie = new Movie(imgSrc,title, genre, description, null);

        List<Seat> startSeatsArray = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Seat seat = new Seat(i, 0, false);
            seat.setMovie(movie);
            startSeatsArray.add(seat);
        }

        movie.setSeats(startSeatsArray);
        return movie;
    }
}