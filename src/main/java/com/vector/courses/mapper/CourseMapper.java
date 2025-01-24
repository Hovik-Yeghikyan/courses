package com.vector.courses.mapper;

import com.vector.courses.dto.CourseDto;
import com.vector.courses.dto.SaveCourseDto;
import com.vector.courses.entity.Course;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {

    CourseDto toDto(Course course);

    List<CourseDto> toDtoList(List<Course> courses);

    Course toEntity(SaveCourseDto saveCourseDto);

}
