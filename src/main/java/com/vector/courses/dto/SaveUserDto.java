package com.vector.courses.dto;

import com.vector.courses.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveUserDto {

    private String name;
    private String surname;
    private String email;
    private Role role;
}
