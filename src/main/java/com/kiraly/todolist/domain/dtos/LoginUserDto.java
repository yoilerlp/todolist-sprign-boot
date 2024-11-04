package com.kiraly.todolist.domain.dtos;

import jakarta.validation.constraints.Email;
import lombok.*;

import jakarta.validation.constraints.NotBlank;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginUserDto {
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
}
