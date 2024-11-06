package com.kiraly.todolist.controllers;

import com.kiraly.todolist.domain.dtos.TaskDto;
import com.kiraly.todolist.domain.entities.TaskEntity;
import com.kiraly.todolist.domain.entities.UserEntity;
import com.kiraly.todolist.exceptions.RequestBasicException;
import com.kiraly.todolist.mappers.Mapper;
import com.kiraly.todolist.responses.ApiResponse;
import com.kiraly.todolist.services.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final Mapper<TaskEntity, TaskDto> taskMapper;

    @PostMapping()
    public ResponseEntity<ApiResponse<?>> createTask(
            @Valid @RequestBody() TaskDto taskDto,
            @AuthenticationPrincipal UserEntity currentUser
    ) {
        var taskToCreate = taskMapper.mapFrom(taskDto);
        System.out.println("User logged in: " + currentUser);
        System.out.println(currentUser);
        taskToCreate.setUser(currentUser);

        var taskCreated = taskService.createTask(taskToCreate);
        System.out.println(taskCreated);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.builder()
                        .message("Task created")
                        .statusCode(HttpStatus.CREATED.value())
                        .data(taskMapper.mapTo(taskCreated))
                        .build()
        );
    }

    @GetMapping(path = "")
    public ResponseEntity<ApiResponse<?>> getTasksByUser(
            @AuthenticationPrincipal UserEntity currentUser,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "9") int limit
    ) {
        page = page < 0 ? 0 : page;
        limit = limit < 1 ? 9 : limit;

        Pageable pageable = PageRequest.of(page, limit, Sort.by("createdAt").ascending());

        var result = taskService.findByUserId(currentUser.getId(), pageable);

        var tasksList = result.getContent().stream().map(taskMapper::mapTo).toList();

        return ResponseEntity.status(HttpStatus.OK).body(
                ApiResponse.builder()
                        .message("Tasks found")
                        .statusCode(HttpStatus.OK.value())
                        .data(tasksList)
                        .build()
        );

    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getTaskById(
            @AuthenticationPrincipal UserEntity currentUser,
            @PathVariable(value = "id") Long taskId
    ) {
        var taskResult = taskService.findOneByIdAndUserId(taskId, currentUser.getId());

        if (taskResult.isEmpty()) {
            throw RequestBasicException.builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .message("Task not found")
                    .build();
        } else {
            var task = taskResult.get();
            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse.builder()
                            .message("Task found")
                            .statusCode(HttpStatus.OK.value())
                            .data(taskMapper.mapTo(task))
                            .build()
            );
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteTaskById(
            @AuthenticationPrincipal UserEntity currentUser,
            @PathVariable(value = "id") Long taskId
    ) {
        var taskResult = taskService.findOneByIdAndUserId(taskId, currentUser.getId());

        if (taskResult.isEmpty()) {
            throw RequestBasicException.builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .message("Task not found")
                    .build();
        } else {
            taskService.deleteOneById(taskId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse.builder()
                            .message("Task deleted")
                            .statusCode(HttpStatus.OK.value())
                            .data("Task deleted")
                            .build()
            );
        }
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<?> updateTask(
            @AuthenticationPrincipal UserEntity currentUser,
            @PathVariable(value = "id") Long taskId,
            @RequestBody TaskDto taskDto
    ){
        var taskResult = taskService.findOneByIdAndUserId(taskId, currentUser.getId());

        if (taskResult.isEmpty()) {
            throw RequestBasicException.builder()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .message("Task not found")
                    .build();
        } else {

            var currentTask = taskResult.get();
            var taskToUpdated =  taskService.updateTask(
                    taskId,
                    taskMapper.merge(taskDto, currentTask)
            );


            return ResponseEntity.status(HttpStatus.OK).body(
                    ApiResponse.builder()
                            .message("Task Updated")
                            .statusCode(HttpStatus.OK.value())
                            .data(taskMapper.mapTo(taskToUpdated))
                            .build()
            );
        }
    }




}
