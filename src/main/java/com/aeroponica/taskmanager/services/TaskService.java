package com.aeroponica.taskmanager.services;

import com.aeroponica.taskmanager.models.Task;
import com.aeroponica.taskmanager.models.TaskStatus;
import com.aeroponica.taskmanager.repositories.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findByUserId(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    @Transactional
    public Task create(Task task) {
        // toda tarefa nova começa como PENDENTE
        task.setStatus(TaskStatus.PENDENTE);
        return taskRepository.save(task);
    }

    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }

    public Task updateStatus(Long id, TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tarefa não encontrada"));
        task.setStatus(status);
        return taskRepository.save(task);
    }
}