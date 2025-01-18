package com.vector.courses.dto;

import com.vector.courses.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveCourseDto {

    private String title;
    private String description;
    private User teacher;
}
