package com.example.moviebooking.service;

import com.example.moviebooking.model.Seat;
import java.util.List;

public interface SeatService {
    //List<Seat> getSeatsByMovie(Long movieId);
    Seat bookSeat(Long seatId);
}