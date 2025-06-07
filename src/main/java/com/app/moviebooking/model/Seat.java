package com.app.moviebooking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int seatNumber;
    private int number;
    private boolean booked;

//    @ManyToOne
//    @JoinColumn(name = "movie_id")
//    @JsonBackReference
//    private Movie movie;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    @JsonBackReference
    private Schedule schedule;

    public Seat () {
    }

    public Seat(int seatNumber, int number, boolean booked) {
        this.seatNumber = seatNumber;
        this.number = number;
        this.booked = booked;
    }

    public static ArrayList<Seat> getStart () {
        ArrayList<Seat> startSeatsArray = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Seat seat = new Seat(0, i, false);
            startSeatsArray.add(seat);
        }
        return startSeatsArray;
    }
    // getters и setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRow() {
        return seatNumber;
    }

    public void setRow(int row) {
        this.seatNumber = row;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

//    public Movie getMovie() {
//        return movie;
//    }
//
//    public void setMovie(Movie movie) {
//        this.movie = movie;
//    }
}
