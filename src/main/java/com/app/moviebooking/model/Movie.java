package com.app.moviebooking.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgSrc;
    private String title;
    private String description;
    private String genre;

//    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
//    List<Seat> seats;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedules = new ArrayList<>();

    public Movie(String imgSrc, String title, String genre, String description, List<Schedule> schedules) {
        this.imgSrc = imgSrc;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.schedules = schedules;
    }
    public Movie() {
    }
    // getters и setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getGenre() {
        return genre;
    }
    public void setGenre(String title) {
        this.title = genre;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public List<Seat> getSeats(String date) {
//        return seats.get(date);
//    }
//
//    public void setSeats(Map<String, List<Seat>> seats) {
//        this.seats = seats;
//    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}
