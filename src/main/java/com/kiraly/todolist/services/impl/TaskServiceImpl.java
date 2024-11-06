package com.kiraly.todolist.services.impl;

import com.kiraly.todolist.domain.entities.TaskEntity;
import com.kiraly.todolist.repositories.TaskRepository;
import com.kiraly.todolist.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public TaskEntity createTask(TaskEntity taskEntity) {
        return taskRepository.save(taskEntity);
    }

    @Override
    public Optional<TaskEntity> finOneById(Long taskId) {
        return taskRepository.findById(taskId);
    }

    @Override
    public Page<TaskEntity> findByUserId(Integer userId, Pageable pageable) {
        return taskRepository.findByUserId(userId, pageable);
    }

    @Override
    public Optional<TaskEntity> findOneByIdAndUserId(Long taskId, Integer userId) {
        return taskRepository.findByIdAndUserId(taskId, userId);
    }

    @Override
    public void deleteOneById(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    public TaskEntity updateTask(Long taskId, TaskEntity taskEntity) {
        taskEntity.setId(taskId);
        return taskRepository.save(taskEntity);
    }
}
