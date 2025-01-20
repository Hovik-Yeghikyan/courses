package com.vector.courses.service;

import com.vector.courses.dto.CourseDto;
import com.vector.courses.dto.SaveCourseDto;
import com.vector.courses.dto.UserDto;

import java.util.List;

public interface CourseService {


    List<CourseDto> findAll();

    CourseDto save(SaveCourseDto saveCourseDto);

    CourseDto edit(int id, SaveCourseDto saveCourseDto);

    void registerStudentToCourse(int courseId, int studentId);

    List<UserDto> findStudentsByCourseId(int courseId);

    void deleteById(int id);

    CourseDto findById(int id);
}
