package com.aeroponica.taskmanager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "tasks")
@Data // Lombok: Gera automaticamente Getters, Setters, etc.
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    @Size(min = 3, max = 100, message = "O título deve ter entre 3 e 100 caracteres")
    private String title;

    @Size(max = 255)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.PENDENTE;
// O "= TaskStatus.PENDENTE" começa padrão.

    // Relacionamento: Muitas tarefas pertencem a um usuário
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}