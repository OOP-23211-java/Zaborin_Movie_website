package com.example.moviebooking.repository;

import com.example.moviebooking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByMovieId(Long movieId);
}