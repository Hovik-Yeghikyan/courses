package com.vector.courses.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String surname;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;

    @ManyToMany(mappedBy = "students")
    private List<Course> coursesAsStudent;
}
