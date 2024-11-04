package com.kiraly.todolist.repositories;

import com.kiraly.todolist.domain.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String username);
    boolean existsByEmail(String email);
}
