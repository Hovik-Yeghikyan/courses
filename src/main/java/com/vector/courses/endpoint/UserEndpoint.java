package com.vector.courses.endpoint;

import com.vector.courses.dto.*;
import com.vector.courses.entity.User;
import com.vector.courses.mapper.UserMapper;
import com.vector.courses.service.UserService;
import com.vector.courses.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserEndpoint {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;


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
    @PostMapping("/auth")
    public ResponseEntity<?> login(@RequestBody UserAuthRequest userAuthRequest) {
        Optional<User> byEmail = userService.findEntityByEmail(userAuthRequest.getEmail());
        if (byEmail.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .build();
        }
        User user = byEmail.get();
        if (passwordEncoder.matches(userAuthRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.ok(UserAuthResponse.builder()
                    .token(jwtTokenUtil.generateToken(user.getEmail()))
                    .name(user.getName())
                    .surname(user.getSurname())
                    .userId(user.getId())
                    .build());
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SaveUserDto saveUserDto) {
        if (userService.findByEmail(saveUserDto.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        userService.save(saveUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
