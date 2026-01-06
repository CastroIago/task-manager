package com.aeroponica.taskmanager.repositories;

import com.aeroponica.taskmanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Metodos JpaRepository  -> save(), findById(), deleteById() e findAll().
}