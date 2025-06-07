package com.app.moviebooking.service;

import com.app.moviebooking.exception.EmailAlreadyRegisteredException;
import com.app.moviebooking.exception.UsernameAlreadyTakenException;
import com.app.moviebooking.model.User;
import com.app.moviebooking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Сервис для управления пользователями: регистрация, поиск и т.д.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Регистрирует нового пользователя.
     *
     * @param user объект User с незашифрованным паролем
     * @return сохранённый пользователь с зашифрованным паролем
     * @throws UsernameAlreadyTakenException   если указанный логин уже занят
     * @throws EmailAlreadyRegisteredException если указанный email уже зарегистрирован
     */
    public User registerNewUser(User user) {
        userRepository.findByUsername(user.getUsername())
                .ifPresent(u -> {
                    throw new UsernameAlreadyTakenException(user.getUsername());
                });

        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new EmailAlreadyRegisteredException(user.getEmail());
                });

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
}