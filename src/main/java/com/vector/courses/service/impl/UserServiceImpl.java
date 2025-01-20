package com.vector.courses.service.impl;

import com.vector.courses.converter.CourseConverter;
import com.vector.courses.converter.UserConverter;
import com.vector.courses.dto.CourseDto;
import com.vector.courses.dto.SaveUserDto;
import com.vector.courses.dto.UserDto;
import com.vector.courses.entity.Course;
import com.vector.courses.entity.User;
import com.vector.courses.repository.CourseRepository;
import com.vector.courses.repository.UserRepository;
import com.vector.courses.service.CourseService;
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
    private final UserConverter userConverter;
    private final CourseRepository courseRepository;
    private final CourseConverter courseConverter;


    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(userConverter.fromUserEntityToDto(user));
        }
        return userDtos;
    }

    @Override
    public UserDto save(SaveUserDto saveUserDto) {
        User user = userRepository.save(userConverter.fromUserDtoToEntity(saveUserDto));
        return userConverter.fromUserEntityToDto(user);
    }


    @Override
    public UserDto edit(int id, SaveUserDto userDto) {
        User userById = userRepository.findById(id)
                .orElseThrow(() -> {
                    String message = "Cannot find user with id " + id;
                    return new RuntimeException(message);
                });

        userById.setName(userDto.getName());
        userById.setSurname(userDto.getSurname());
        userById.setEmail(userDto.getEmail());
        userById.setRole(userDto.getRole());

        User updatedUser = userRepository.save(userById);
        return userConverter.fromUserEntityToDto(updatedUser);
    }

    @Override
    public List<CourseDto> findUsersCourses(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    String message = "Cannot find user with id " + id;
                    return new RuntimeException(message);
                });
        List<CourseDto> courses = new ArrayList<>();
        List<Course> all = courseRepository.findAll();
        for (Course course : all) {
            if (course.getStudents().contains(user)) {
                courses.add(courseConverter.fromCourseEntityToDto(course));
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
        return userConverter.fromUserEntityToDto(user);

    }

    @Override
    public Optional<UserDto> findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return Optional.empty();
        }
        UserDto userDto = userConverter.fromUserEntityToDto(user);
        return Optional.of(userDto);
    }
}
