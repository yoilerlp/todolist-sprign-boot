package com.kiraly.todolist.controllers;

import com.kiraly.todolist.domain.entities.UserEntity;
import com.kiraly.todolist.domain.dtos.UserDto;
import com.kiraly.todolist.mappers.Mapper;
import com.kiraly.todolist.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final Mapper<UserEntity, UserDto> userMapper;

    @GetMapping(path = "/token-info")
    public ResponseEntity<ApiResponse<?>> getCurrentUser(@AuthenticationPrincipal UserEntity userDetails) {
        return ResponseEntity.ok(
                ApiResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .data(userMapper.mapTo(userDetails))
                        .build()
        );
    }

}
