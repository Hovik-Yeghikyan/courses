package com.vector.courses.converter;

import com.vector.courses.dto.SaveUserDto;
import com.vector.courses.dto.UserDto;
import com.vector.courses.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {


    public UserDto fromUserEntityToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public User fromUserDtoToEntity(SaveUserDto saveUserDto) {
        return User.builder()
                .name(saveUserDto.getName())
                .surname(saveUserDto.getSurname())
                .email(saveUserDto.getEmail())
                .role(saveUserDto.getRole())
                .build();
    }


}
