package com.aeroponica.taskmanager.controllers;

import com.aeroponica.taskmanager.models.User;
import com.aeroponica.taskmanager.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users") // Define a rota base para usuários
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Rota para Cadastrar Usuário (POST)
    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User user) {
        User newUser = userService.create(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    // Rota para Listar todos os usuários (GET)
    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
}