package com.aeroponica.taskmanager.services;

import com.aeroponica.taskmanager.models.User;
import com.aeroponica.taskmanager.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importe isso
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder; // Adicione isso

    // Atualize o construtor para incluir o encoder
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User create(User user) {
        // 1. Email deve ser único
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("Email já cadastrado");
                });

        // 2. CRIPTOGRAFA a senha antes de salvar
        String senhaCriptografada = passwordEncoder.encode(user.getPassword());
        user.setPassword(senhaCriptografada);

        return userRepository.save(user);
    }

    // 3. NOVO MÉTODO: Lógica de Login
    public String login(String email, String password) {
        // Procura o usuário
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        // Compara a senha enviada (plana) com a do banco (criptografada)
        if (passwordEncoder.matches(password, user.getPassword())) {
            // Se as senhas baterem, por enquanto retornamos uma mensagem de sucesso
            // No próximo passo, geraremos um Token JWT real aqui.
            return "Login realizado com sucesso! Token: SIMULADO_JWT_123";
        } else {
            throw new RuntimeException("Senha incorreta!");
        }
    }
}