package com.vector.courses.service.impl;

import com.vector.courses.dto.CourseDto;
import com.vector.courses.dto.SaveCourseDto;
import com.vector.courses.dto.UserDto;
import com.vector.courses.entity.Course;
import com.vector.courses.entity.User;
import com.vector.courses.exception.ResourceNotFoundException;
import com.vector.courses.mapper.CourseMapper;
import com.vector.courses.mapper.UserMapper;
import com.vector.courses.repository.CourseRepository;
import com.vector.courses.repository.UserRepository;
import com.vector.courses.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseMapper courseMapper;
    private final UserMapper userMapper;


    @Override
    public List<CourseDto> findAll() {
        List<Course> courses = courseRepository.findAll();
        return courseMapper.toDtoList(courses);
    }

    @Override
    public CourseDto save(SaveCourseDto saveCourseDto) {
        Course course = courseRepository.save(courseMapper.toEntity(saveCourseDto));
        return courseMapper.toDto(course);
    }

    @Override
    public CourseDto edit(int id, SaveCourseDto saveCourseDto) {
        Course courseById = courseRepository.findById(id)
                .orElseThrow(() -> {
                    String message = "Cannot find course with id " + id;
                    return new ResourceNotFoundException(message);
                });

        courseById.setTitle(saveCourseDto.getTitle());
        courseById.setDescription(saveCourseDto.getDescription());
        courseById.setTeacher(saveCourseDto.getTeacher());

        Course updatedCourse = courseRepository.save(courseById);
        return courseMapper.toDto(updatedCourse);
    }

    @Override
    public void registerStudentToCourse(int courseId, int studentId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    String message = "Cannot find course with id " + courseId;
                    return new ResourceNotFoundException(message);
                });
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> {
                    String message = "Cannot find student with id " + studentId;
                    return new ResourceNotFoundException(message);
                });
        course.getStudents().add(student);
        courseRepository.save(course);

    }

    @Override
    public List<UserDto> findStudentsByCourseId(int courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    String message = "Cannot find course with id " + courseId;
                    return new ResourceNotFoundException(message);
                });
        List<UserDto> students = new ArrayList<>();
        List<User> userByCourse = course.getStudents();
        for (User user : userByCourse) {
            students.add(userMapper.toDto(user));
        }
        return students;
    }


    @Override
    public void deleteById(int id) {
        if (!courseRepository.existsById(id)) {
            String message = "Cannot find course with id " + id;
            throw new ResourceNotFoundException(message);
        }
        courseRepository.deleteById(id);
    }

    @Override
    public CourseDto findById(int id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            return null;
        }
        return courseMapper.toDto(course);

    }


}
