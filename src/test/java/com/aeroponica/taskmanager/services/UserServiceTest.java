package com.aeroponica.taskmanager.services;

import com.aeroponica.taskmanager.models.User;
import com.aeroponica.taskmanager.repositories.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("Não deve cadastrar usuário com e-mail que já existe")
    void deveLancarErroQuandoEmailJaExiste() {
        // Arrange
        User usuarioExistente = new User();
        usuarioExistente.setEmail("iago@teste.com");

        when(userRepository.findByEmail("iago@teste.com"))
                .thenReturn(Optional.of(usuarioExistente));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            User novoUser = new User();
            novoUser.setEmail("iago@teste.com");
            userService.create(novoUser);
        });

        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve cadastrar usuário com sucesso quando o e-mail não existe")
    void deveCadastrarUsuarioComSucesso() {
        // Arrange
        User novoUser = new User();
        novoUser.setEmail("novo@teste.com");
        novoUser.setPassword("123456");

        // Simulando que o email NÃO existe no banco
        when(userRepository.findByEmail("novo@teste.com")).thenReturn(Optional.empty());

        // Simulando que o encoder vai devolver uma senha criptografada qualquer
        when(passwordEncoder.encode("123456")).thenReturn("senhaCriptografada123");

        // Simulando que o banco vai salvar e retornar o usuário
        when(userRepository.save(any(User.class))).thenReturn(novoUser);

        // Act
        User resultado = userService.create(novoUser);

        // Assert
        assertNotNull(resultado);
        assertEquals("novo@teste.com", resultado.getEmail());

        // Verifica se o passwordEncoder foi realmente usado
        verify(passwordEncoder, times(1)).encode("123456");

        // Verifica se o save do banco foi chamado exatamente 1 vez
        verify(userRepository, times(1)).save(any());
    }
}