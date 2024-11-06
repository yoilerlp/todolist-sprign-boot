package com.kiraly.todolist.services;

import com.kiraly.todolist.domain.entities.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface TaskService {
    TaskEntity createTask(TaskEntity taskEntity);
    Optional<TaskEntity> finOneById(Long taskId);
    Page<TaskEntity> findByUserId(Integer userId, Pageable pageable);
    Optional<TaskEntity> findOneByIdAndUserId(Long taskId, Integer userId);
    void deleteOneById(Long taskId);
    TaskEntity updateTask(Long taskId, TaskEntity taskEntity);
}
