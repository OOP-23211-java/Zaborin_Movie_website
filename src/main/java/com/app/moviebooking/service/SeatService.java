package com.app.moviebooking.service;

import com.app.moviebooking.model.Seat;

public interface SeatService {
    //List<Seat> getSeatsByMovie(Long movieId);
    Seat bookSeat(Long seatId);
}