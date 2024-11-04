package com.kiraly.todolist.services;

import com.kiraly.todolist.domain.entities.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public interface UserService {
    UserEntity createUser(UserEntity user);
    Boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(@NotBlank @Email String email);
}
