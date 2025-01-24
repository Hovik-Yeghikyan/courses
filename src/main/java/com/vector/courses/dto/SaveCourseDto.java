package com.vector.courses.dto;

import com.vector.courses.entity.User;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveCourseDto {

    @Size(min = 2,message = "Title should be >= 2")
    private String title;
    @Size(min = 2,message = "Description should be >= 2")
    private String description;
    private User teacher;
}
