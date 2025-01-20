package com.vector.courses.endpoint;

import com.vector.courses.converter.UserConverter;
import com.vector.courses.dto.CourseDto;
import com.vector.courses.dto.SaveUserDto;
import com.vector.courses.dto.UserDto;
import com.vector.courses.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserEndpoint {

    private final UserService userService;


    @GetMapping()
    public ResponseEntity<List<UserDto>> getAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/{id}/courses")
    public ResponseEntity<List<CourseDto>> getStudentsCourses(@PathVariable("id") int id) {
        return ResponseEntity.ok(userService.findUsersCourses(id));
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody SaveUserDto saveUserDto) {
        if (userService.findByEmail(saveUserDto.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        UserDto user = userService.save(saveUserDto);
        return ResponseEntity.ok(user);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> edit(@PathVariable("id") int id, @RequestBody SaveUserDto saveUserDto) {
        UserDto updatedUser = userService.edit(id, saveUserDto);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        if (userService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
