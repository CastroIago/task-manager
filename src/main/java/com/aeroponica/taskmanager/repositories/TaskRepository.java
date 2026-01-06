package com.aeroponica.taskmanager.repositories;

import com.aeroponica.taskmanager.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Método costumizado: o Spring cria a consulta SQL automaticamente baseado no nome!
    // Isso vai procurar todas as tarefas onde o ID do utilizador seja o que passarmos.
    List<Task> findByUserId(Long userId);
}