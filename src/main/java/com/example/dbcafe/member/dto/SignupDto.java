package com.example.dbcafe.member.dto;

import com.example.dbcafe.member.entity.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SignupDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String name;
    private String gender;
    private String phone;
    private String role;
    private LocalDateTime createDate;

    public User toEntity() {
        return User.builder()
                .id(id)
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .gender(gender)
                .phone(phone)
                .role(role)
                .createDate(createDate)
                .build();
    }
}