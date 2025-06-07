package com.app.moviebooking.repository;

import com.app.moviebooking.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    //List<Seat> findByMovieId(Long movieId);
}