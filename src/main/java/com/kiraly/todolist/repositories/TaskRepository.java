package com.kiraly.todolist.repositories;

import com.kiraly.todolist.domain.entities.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    Page<TaskEntity> findByUserId(Integer userId, Pageable pageable);
    Optional<TaskEntity> findByIdAndUserId(Long id, Integer userId);
}
