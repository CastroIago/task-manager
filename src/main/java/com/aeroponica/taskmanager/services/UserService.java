package com.aeroponica.taskmanager.services;

import com.aeroponica.taskmanager.models.User;
import com.aeroponica.taskmanager.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Construtor para injeção de dependência
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional // Garante que a operação seja atômica no banco
    public User create(User user) {
        // email deve ser único
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("Email já cadastrado");
                });

        return userRepository.save(user);
    }
}