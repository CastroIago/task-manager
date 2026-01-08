package com.aeroponica.taskmanager.controllers;

import com.aeroponica.taskmanager.dtos.LoginRequest;
import com.aeroponica.taskmanager.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Chama a lógica de autenticação que criamos no UserService
            String result = userService.login(loginRequest.email(), loginRequest.password());
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            // Se a senha estiver errada ou usuário não existir, retorna 401 Unauthorized
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}