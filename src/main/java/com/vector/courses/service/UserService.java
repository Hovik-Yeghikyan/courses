package com.vector.courses.service;

import com.vector.courses.dto.CourseDto;
import com.vector.courses.dto.SaveUserDto;
import com.vector.courses.dto.UserDto;
import com.vector.courses.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDto> findAll();

    UserDto save(SaveUserDto saveUserDto);


    UserDto edit(int id, SaveUserDto saveUserDto);

    List<CourseDto> findUsersCourses(int id);

    void deleteById(int id);

    UserDto findById(int id);

    Optional<UserDto> findByEmail(String email);
    Optional<User> findEntityByEmail(String email);
}
