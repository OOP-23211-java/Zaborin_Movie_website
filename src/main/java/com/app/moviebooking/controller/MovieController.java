package com.app.moviebooking.controller;

import com.app.moviebooking.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
/**
 * Контроллер для управления страницами.
 */
@Controller
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    /**
     * Отображает список всех фильмов на главной странице.
     *
     * @param model модель для передачи данных в шаблон
     * @return имя шаблона index.html
     */
    @GetMapping("/")
    public String listMovies(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "index";
    }
    /**
     * Отображает страницу описания выбранного фильма.
     *
     * @param id    идентификатор фильма
     * @param model модель для передачи данных в шаблон
     * @return имя шаблона movieDescription.html
     */
    @GetMapping("/movie/{id}/")
    public String movieDescription(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        return "movieDescription";
    }
    /**
     * Отображает страницу выбора даты для сеанса фильма.
     *
     * @param id    идентификатор фильма
     * @param model модель для передачи данных в шаблон
     * @return имя шаблона chooseDate.html
     */
    @GetMapping("/movie/{id}/chooseDate")
    public String selectSeats1(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        return "chooseDate";
    }
    /**
     * Отображает страницу выбора мест для сеанса выбранной даты.
     *
     * @param id    идентификатор фильма
     * @param date  дата сеанса в формате строки
     * @param model модель для передачи данных в шаблон
     * @return имя шаблона seats.html
     */
    @GetMapping("/movie/{id}/chooseDate/{date}")
    public String selectSeats2(@PathVariable Long id, @PathVariable String date, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        return "seats";
    }
}