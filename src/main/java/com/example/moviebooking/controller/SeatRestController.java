package com.example.moviebooking.controller;

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

    @GetMapping("/{movieId}")
    public List<Seat> getSeats(@PathVariable Long movieId) {
        //return seatService.getSeatsByMovie(movieId);
        return movieService.getSeatById(movieId);
    }

    @PostMapping("/book/{seatId}")
    public Seat bookSeat(@PathVariable Long seatId) {
        return seatService.bookSeat(seatId);
    }
}