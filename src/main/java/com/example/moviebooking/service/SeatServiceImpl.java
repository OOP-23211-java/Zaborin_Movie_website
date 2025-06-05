package com.example.moviebooking.service;

import com.example.moviebooking.model.Seat;
import com.example.moviebooking.repository.SeatRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    public SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public List<Seat> getSeatsByMovie(Long movieId) {
        return seatRepository.findByMovieId(movieId);
    }

    @Override
    public Seat bookSeat(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
        seat.setBooked(true);
        return seatRepository.save(seat);
    }
}