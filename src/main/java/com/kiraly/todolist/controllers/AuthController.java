package com.kiraly.todolist.controllers;

import com.kiraly.todolist.domain.entities.UserEntity;
import com.kiraly.todolist.domain.dtos.LoginUserDto;
import com.kiraly.todolist.domain.dtos.UserDto;
import com.kiraly.todolist.exceptions.RequestBasicException;
import com.kiraly.todolist.exceptions.UserNotFoundException;
import com.kiraly.todolist.mappers.Mapper;
import com.kiraly.todolist.responses.ApiResponse;
import com.kiraly.todolist.services.UserService;
import com.kiraly.todolist.services.impl.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final Mapper<UserEntity, UserDto> userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping(path = "/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody() LoginUserDto loginUserDto) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword()));

            var authUser = userService.findByEmail(loginUserDto.getEmail()).orElseThrow(() -> new UserNotFoundException("User not found"));

            String jwtToken = jwtService.generateToken(authUser);

            Map<String, Object> loginData = new HashMap<>();
            loginData.put("token", jwtToken);
            loginData.put("user", userMapper.mapTo(authUser));

            return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.builder().data(loginData).statusCode(HttpStatus.OK.value()).message("Login successful").build());

        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.builder().statusCode(HttpStatus.UNAUTHORIZED.value()).message("Invalid credentials").build());
            }
            throw e;
        }

    }

    @PostMapping(path = "/register")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody UserDto userDto) {
        var userExist = userService.existsByEmail(userDto.getEmail());
        if (userExist) {
            throw new RequestBasicException(HttpStatus.BAD_REQUEST.value(), "User already exists");
        }
        var userToCreate = userMapper.mapFrom(userDto);
        var userCreated = userService.createUser(userToCreate);
        var result = ApiResponse.builder().data(userMapper.mapTo(userCreated)).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
