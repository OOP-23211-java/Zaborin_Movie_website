package com.example.moviebooking.controller;

import com.example.moviebooking.model.User;
import com.example.moviebooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationForm", new User());
        return "registration";   // возвращаем шаблон registration.html
    }

    @PostMapping("/registration")
    public String processRegistration(
            @ModelAttribute("registrationForm") @Valid User form,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "registration";
        }


        try {
            User newUser = new User();
            newUser.setUsername(form.getUsername());
            newUser.setPassword(form.getPassword());
            newUser.setEmail(form.getEmail());

            userService.registerNewUser(newUser);
        } catch (RuntimeException ex) {
            model.addAttribute("registrationError", ex.getMessage());
            return "registration";
        }

        return "redirect:/login?registered";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";   // вернём шаблон login.html
    }
}
