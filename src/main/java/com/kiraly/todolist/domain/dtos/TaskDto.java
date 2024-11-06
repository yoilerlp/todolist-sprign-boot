package com.kiraly.todolist.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kiraly.todolist.domain.entities.TaskStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDto {
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    @Size(min = 1, max = 500)
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @FutureOrPresent
    private LocalDateTime dueDate;

    @NotNull
    private TaskStatus status;
}
