package com.aeroponica.taskmanager.repositories;

import com.aeroponica.taskmanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    // Metodos JpaRepository  -> save(), findById(), deleteById() e findAll().
}