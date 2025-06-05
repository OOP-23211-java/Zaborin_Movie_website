package com.example.moviebooking.config;

import com.example.moviebooking.model.Movie;
import com.example.moviebooking.model.Schedule;
import com.example.moviebooking.model.Seat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleInitializer {

    public static Schedule createScheduleWithEmptySeats(Movie movie, String date) {
        // Создаем расписание на указанную дату
        Schedule schedule = new Schedule();
        schedule.setDate(date);
        schedule.setMovie(movie);

        // Генерируем пустые места
        List<Seat> seats = generateEmptySeats(schedule);
        schedule.setSeats(seats);

        return schedule;
    }

    private static List<Seat> generateEmptySeats(Schedule schedule) {
        List<Seat> seats = new ArrayList<>();

        // Проходим по всем рядам
        for (int row = 0; row < 4; row++) {
            // Создаем места в ряду
            for (int number = 1; number <= 4; number++) {
                Seat seat = new Seat();
                seat.setRow(row);
                seat.setNumber(number);
                seat.setBooked(false);
                seat.setSchedule(schedule);  // Привязываем к расписанию
                seats.add(seat);
            }
        }

        return seats;
    }
}
