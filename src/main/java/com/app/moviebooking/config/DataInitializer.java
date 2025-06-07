package com.app.moviebooking.config;

import com.app.moviebooking.model.Movie;
import com.app.moviebooking.model.Schedule;
import com.app.moviebooking.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {
    /**
     * Инициализация фильмов и расписаний в репозитории.
     *
     * @param movieRepository репозиторий фильмов
     * @return CommandLineRunner для выполнения инициализации
     */
    @Bean
    CommandLineRunner initMovies(MovieRepository movieRepository) {

        return args -> {
            movieRepository.save(createMovie("kojan.jpg", "ДГМА", "Психологический хоррор, триллер", "...", "10"));
            movieRepository.save(createMovie("START.jpg", "Начало", "Какие-то жанры", "Кино про сны внутри снов", "21"));
            movieRepository.save(createMovie("START.jpg", "Начало", "Какие-то жанры", "Кино про сны внутри снов", "12"));

        };

    }
    /**
     * Создает объект Movie с одним расписанием.
     *
     * @param imgSrc      путь к изображению постера
     * @param title       название фильма
     * @param genre       жанр фильма
     * @param description описание фильма
     * @param date        дата сеанса в формате строки
     * @return объект Movie с расписаниями
     */
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