package com.vector.courses.service.impl;

import com.vector.courses.converter.CourseConverter;
import com.vector.courses.converter.UserConverter;
import com.vector.courses.dto.CourseDto;
import com.vector.courses.dto.SaveCourseDto;
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
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseConverter courseConverter;



    @Override
    public List<CourseDto> findAll() {
        List<Course> courses = courseRepository.findAll();
        List<CourseDto> courseDtos = new ArrayList<>();
        for (Course course : courses) {
            courseDtos.add(courseConverter.fromCourseEntityToDto(course));
        }
        return courseDtos;
    }

    @Override
    public CourseDto save(SaveCourseDto saveCourseDto) {
        Course course = courseRepository.save(courseConverter.fromCourseDtoToEntity(saveCourseDto));
        return courseConverter.fromCourseEntityToDto(course);
    }

    @Override
    public CourseDto edit(int id, SaveCourseDto saveCourseDto) {
        Course courseById = courseRepository.findById(id)
                .orElseThrow(() -> {
                    String message = "Cannot find course with id " + id;
                    return new RuntimeException(message);
                });

        courseById.setTitle(saveCourseDto.getTitle());
        courseById.setDescription(saveCourseDto.getDescription());
        courseById.setTeacher(saveCourseDto.getTeacher());

        Course updatedCourse = courseRepository.save(courseById);
        return courseConverter.fromCourseEntityToDto(updatedCourse);
    }

    @Override
    public void registerStudentToCourse(int courseId, SaveUserDto student) {
        Course courseById = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    String message = "Cannot find course with id " + courseId;
                    return new RuntimeException(message);
                });
        Optional<User> byEmail = userRepository.findByEmail(student.getEmail());
        User user = byEmail.get();
        courseById.getStudents().add(user);
        user.getCoursesAsStudent().add(courseById);

        courseRepository.save(courseById);
        userRepository.save(user);
        }


    @Override
    public void deleteById(int id) {
        courseRepository.deleteById(id);
    }

    @Override
    public CourseDto findById(int id) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course == null) {
            return null;
        }
        return courseConverter.fromCourseEntityToDto(course);

    }


}
