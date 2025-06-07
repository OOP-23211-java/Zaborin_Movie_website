package com.app.moviebooking.config;

import com.app.moviebooking.model.Movie;
import com.app.moviebooking.model.Schedule;
import com.app.moviebooking.model.Seat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * Утилита для создания расписаний с пустыми местами.
 */
@Service
public class ScheduleInitializer {
    /**
     * Создает расписание на указанную дату с пустыми местами.
     *
     * @param movie объект фильма
     * @param date  дата сеанса в формате строки
     * @return объект Schedule с созданными местами
     */
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
    /**
     * Генерирует список пустых мест для расписания.
     *
     * @param schedule объект расписания
     * @return список объектов Seat
     */
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
