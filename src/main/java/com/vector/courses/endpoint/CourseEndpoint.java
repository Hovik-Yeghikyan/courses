package com.vector.courses.endpoint;

import com.vector.courses.dto.CourseDto;
import com.vector.courses.dto.SaveCourseDto;
import com.vector.courses.dto.UserDto;
import com.vector.courses.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseEndpoint {


    private final CourseService courseService;


    @GetMapping()
    public ResponseEntity<List<CourseDto>> getAll() {
        return ResponseEntity.ok(courseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(courseService.findById(id));
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<List<UserDto>> getStudentsByCourseId(@PathVariable int courseId) {
        return ResponseEntity.ok(courseService.findStudentsByCourseId(courseId));
    }



    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid SaveCourseDto saveCourseDto) {
        CourseDto courseDto = courseService.save(saveCourseDto);
        return ResponseEntity.ok(courseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") int id,@RequestBody SaveCourseDto saveCourseDto) {
        CourseDto updatedCourse = courseService.edit(id,saveCourseDto);
        return ResponseEntity.ok(updatedCourse);
    }

    @PostMapping("/{courseId}/register")
    public ResponseEntity<String> registerStudentToCourse(@PathVariable int courseId, @RequestBody UserDto student) {
        try {
            courseService.registerStudentToCourse(courseId, student.getId());
            return ResponseEntity.ok("Student successfully registered to the course");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to register student to the course");
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        courseService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
