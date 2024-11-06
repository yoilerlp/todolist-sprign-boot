package com.kiraly.todolist.mappers.impl;

import com.kiraly.todolist.domain.dtos.TaskDto;
import com.kiraly.todolist.domain.entities.TaskEntity;
import com.kiraly.todolist.mappers.Mapper;
import com.kiraly.todolist.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskMapper implements Mapper<TaskEntity, TaskDto> {

    private final ModelMapper modelMapper;

    @Override
    public TaskDto mapTo(TaskEntity taskEntity) {
        return modelMapper.map(taskEntity, TaskDto.class);
    }

    @Override
    public TaskEntity mapFrom(TaskDto taskDto) {
        return modelMapper.map(taskDto, TaskEntity.class);
    }

    @Override
    public TaskEntity merge(TaskDto origin, TaskEntity destination) {
        TaskEntity taskEntityCopy = modelMapper.map(destination, TaskEntity.class);
        modelMapper.map(origin, destination);
        return taskEntityCopy;
    }
}
