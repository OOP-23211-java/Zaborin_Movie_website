package com.app.moviebooking.exception;

public class SeatNotFoundException extends RuntimeException {
    public SeatNotFoundException(Long seatId) {
        super("Seat with id=" + seatId + " not found");
    }
}