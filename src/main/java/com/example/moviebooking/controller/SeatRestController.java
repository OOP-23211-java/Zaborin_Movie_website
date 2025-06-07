package com.example.moviebooking.controller;

import com.example.moviebooking.model.Schedule;
import com.example.moviebooking.model.Seat;
import com.example.moviebooking.service.MovieService;
import com.example.moviebooking.service.SeatService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatRestController {

    private final SeatService seatService;
    private final MovieService movieService;

    public SeatRestController(SeatService seatService, MovieService movieService) {
        this.seatService = seatService;
        this.movieService = movieService;
    }

    /**
     * Возвращает список расписаний по ID фильма.
     *
     * @param movieId идентификатор фильма
     * @return список объектов Schedule
     */
    @GetMapping("/{movieId}")
    public List<Schedule> getSchedule(@PathVariable Long movieId) {
        //return seatService.getSeatsByMovie(movieId);
        return movieService.getScheduleById(movieId);
    }
    /**
     * Возвращает список мест по ID фильма и дате сеанса.
     *
     * @param movieId идентификатор фильма
     * @param date    дата сеанса в формате строки
     * @return список объектов Seat
     */
    @GetMapping("/{movieId}/{date}")
    public List<Seat> getSeats(@PathVariable Long movieId, @PathVariable String date) {
        //return seatService.getSeatsByMovie(movieId);
        return movieService.getSeatById(movieId,date);
    }
    /**
     * Бронирование места по ID.
     *
     * @param seatId идентификатор места
     * @return объект Seat после бронирования
     */
    @PostMapping("/book/{seatId}")
    public Seat bookSeat(@PathVariable Long seatId) {
        return seatService.bookSeat(seatId);
    }
}