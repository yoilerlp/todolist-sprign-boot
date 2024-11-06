package com.kiraly.todolist.mappers.impl;

import com.kiraly.todolist.domain.entities.UserEntity;
import com.kiraly.todolist.domain.dtos.UserDto;
import com.kiraly.todolist.mappers.Mapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<UserEntity, UserDto> {

    private final ModelMapper modelMapper;

    @Override
    public UserDto mapTo(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserEntity mapFrom(UserDto userDto) {
        return modelMapper.map(userDto, UserEntity.class);
    }

    @Override
    public UserEntity merge(UserDto origin, UserEntity destination) {
        return null;
    }
}
