package com.app.moviebooking.service;

import com.app.moviebooking.exception.SeatNotFoundException;
import com.app.moviebooking.model.Seat;
import com.app.moviebooking.repository.SeatRepository;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса для работы с местами (Seats).
 */
@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;

    public SeatServiceImpl(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

//    @Override
//   public List<Seat> getSeatsByMovie(Long movieId) {
//        return seatRepository.findByMovieId(movieId);
//    }
    /**
     * Бронирует место по его идентификатору.
     *
     * @param seatId идентификатор места
     * @return обновлённый объект Seat (с пометкой booked=true)
     * @throws SeatNotFoundException если место не найдено
     */
    @Override
    public Seat bookSeat(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new SeatNotFoundException(seatId));
        seat.setBooked(true);
        return seatRepository.save(seat);
    }
}