package com.vector.courses.service.impl;


import com.vector.courses.dto.CourseDto;
import com.vector.courses.dto.SaveUserDto;
import com.vector.courses.dto.UserDto;
import com.vector.courses.entity.Course;
import com.vector.courses.entity.User;
import com.vector.courses.exception.ResourceAlreadyExistsException;
import com.vector.courses.exception.ResourceNotFoundException;
import com.vector.courses.mapper.CourseMapper;
import com.vector.courses.mapper.UserMapper;
import com.vector.courses.repository.CourseRepository;
import com.vector.courses.repository.UserRepository;
import com.vector.courses.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserMapper userMapper;


    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    @Override
    public UserDto save(SaveUserDto saveUserDto) {
        if (userRepository.findByEmail(saveUserDto.getEmail()).isPresent()) {
       throw new ResourceAlreadyExistsException("User with email " + saveUserDto.getEmail() + " already exists");
        } else {
            User user = userRepository.save(userMapper.toEntity(saveUserDto));
            return userMapper.toDto(user);
        }
    }


    @Override
    public UserDto edit(int id, SaveUserDto userDto) {
        User userById = userRepository.findById(id)
                .orElseThrow(() -> {
                    String message = "Cannot find user with id " + id;
                    return new ResourceNotFoundException(message);
                });

        userById.setName(userDto.getName());
        userById.setSurname(userDto.getSurname());
        userById.setEmail(userDto.getEmail());
        userById.setRole(userDto.getRole());

        User updatedUser = userRepository.save(userById);
        return userMapper.toDto(updatedUser);
    }

    @Override
    public List<CourseDto> findUsersCourses(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    String message = "Cannot find user with id " + id;
                    return new ResourceNotFoundException(message);
                });
        List<CourseDto> courses = new ArrayList<>();
        List<Course> all = courseRepository.findAll();
        for (Course course : all) {
            if (course.getStudents().contains(user)) {
                courses.add(courseMapper.toDto(course));
            }
        }
        return courses;

    }


    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto findById(int id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return null;
        }
        return userMapper.toDto(user);

    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return Optional.empty();
        }
        UserDto userDto = userMapper.toDto(user);
        return Optional.of(userDto);
    }
}
