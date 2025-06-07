package com.app.moviebooking.service;

import com.app.moviebooking.exception.MovieNotFoundException;
import com.app.moviebooking.model.Movie;
import com.app.moviebooking.model.Schedule;
import com.app.moviebooking.model.Seat;
import com.app.moviebooking.repository.MovieRepository;
import org.springframework.stereotype.Service;
import java.util.List;
/**
 * Реализация сервиса для работы с фильмами.
 */
@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    /**
     * Возвращает список всех фильмов.
     *
     * @return список Movie
     */
    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    /**
     * Ищет фильм по идентификатору.
     *
     * @param id идентификатор фильма
     * @return найденный Movie
     * @throws MovieNotFoundException если фильм не найден
     */
    @Override
    public Movie getMovieById(Long id) {
        return movieRepository.findById(id)
                .orElseThrow(() ->  new MovieNotFoundException(id));
    }
    /**
     * Получает расписание по идентификатору фильма.
     *
     * @param id идентификатор фильма
     * @return список Schedule
     * @throws MovieNotFoundException если фильм не найден
     */
    @Override
    public List<Schedule> getScheduleById(Long id) {
        Movie movie = getMovieById(id);
        return movie.getSchedules();
    }
    /**
     * Получает список мест на конкретную дату.
     *
     * @param id   идентификатор фильма
     * @param date дата в формате YYYY-MM-DD
     * @return список Seat для выбранной даты
     * @throws MovieNotFoundException если фильм не найден
     */
    @Override
    public List<Seat> getSeatById(Long id, String date) {
        Movie movie = movieRepository.findById(id).orElseThrow(() ->  new MovieNotFoundException(id));
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