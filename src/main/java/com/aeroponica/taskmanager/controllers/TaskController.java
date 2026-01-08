package com.aeroponica.taskmanager.controllers;

import com.aeroponica.taskmanager.models.Task;
import com.aeroponica.taskmanager.models.TaskStatus;
import com.aeroponica.taskmanager.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // 1. Criar Tarefa -> post
    @PostMapping
    public ResponseEntity<Task> create(@Valid @RequestBody Task task) {
        Task newTask = taskService.create(task);
        return new ResponseEntity<>(newTask, HttpStatus.CREATED);
    }

    // 2. Listar todas as tarefas ->GET
    @GetMapping
    public ResponseEntity<List<Task>> findAll() {
        return ResponseEntity.ok(taskService.findAll());
    }

    // 3. Filtrar por Status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Task>> findByStatus(@PathVariable TaskStatus status) {
        return ResponseEntity.ok(taskService.findByStatus(status));
    }

    // 4. Listar por Usuário
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> findByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(taskService.findByUserId(userId));
    }

    // 5. Atualizar Status da Tarefa (PATCH)
    @PatchMapping("/{id}/status")
    public ResponseEntity<Task> updateStatus(@PathVariable Long id, @RequestBody String status) {
        // Pega a string do Postman e transforma nosso Enum TaskStatus
        TaskStatus newStatus = TaskStatus.valueOf(status.toUpperCase());
        Task updated = taskService.updateStatus(id, newStatus);
        return ResponseEntity.ok(updated);
    }
    // 6. Deletar (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

}