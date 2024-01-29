package com.dosmakhambetbaktiyar.module41.model;

import com.dosmakhambetbaktiyar.module41.enums.UserRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("users")
public class User {
    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ToString.Include(name = "password")
    private String maskPassword(){
        return "*********";
    }
}
