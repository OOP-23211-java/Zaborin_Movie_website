package com.example.moviebooking.controller;

import com.example.moviebooking.model.Movie;
import com.example.moviebooking.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/")
    public String listMovies(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "index";
    }

    @GetMapping("/movie/{id}/seats")
    public String selectSeats(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        return "seats";
    }
    @GetMapping("/movie/{id}/seats1")
    public String selectSeats1(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        return "chooseDate";
    }
    @GetMapping("/movie/{id}/seats1/{date}")
    public String selectSeats2(@PathVariable Long id, @PathVariable String date, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        return "seats1";
    }
}