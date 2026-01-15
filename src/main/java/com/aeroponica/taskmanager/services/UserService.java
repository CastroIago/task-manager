package com.aeroponica.taskmanager.services;

import com.aeroponica.taskmanager.models.User;
import com.aeroponica.taskmanager.repositories.UserRepository;
import com.aeroponica.taskmanager.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importe isso
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // Adicione isso
    private final JwtUtil jwtUtil;

    // Atualize o construtor para incluir o encoder
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User create(User user) {
        // Email deve ser único
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("Email já cadastrado");
                });

        // CRIPTOGRAFA a senha antes de salvar
        String senhaCriptografada = passwordEncoder.encode(user.getPassword());
        user.setPassword(senhaCriptografada);

        return userRepository.save(user);
    }

    // Lógica de Login
    public String login(String email, String password) {
        // Procura o usuário
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Compara a senha enviada (plana) com a do banco (criptografada)
        if (passwordEncoder.matches(password, user.getPassword())) {
            // Gera o token real com base no e-mail do usuário
            return jwtUtil.generateToken(user.getEmail());
        } else {
            throw new RuntimeException("Senha incorreta!");
        }
    }
}