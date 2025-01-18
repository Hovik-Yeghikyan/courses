package com.vector.courses.converter;

import com.vector.courses.dto.CourseDto;
import com.vector.courses.dto.SaveCourseDto;
import com.vector.courses.entity.Course;
import org.springframework.stereotype.Component;

@Component
public class CourseConverter {

    public CourseDto fromCourseEntityToDto(Course course) {
        return CourseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .teacher(course.getTeacher())
                .build();
    }

    public Course fromCourseDtoToEntity(SaveCourseDto saveCourseDto) {
        return Course.builder()
                .title(saveCourseDto.getTitle())
                .description(saveCourseDto.getDescription())
                .teacher(saveCourseDto.getTeacher())
                .build();
    }
}
