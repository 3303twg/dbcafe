package com.example.dbcafe.member.dto;

import com.example.dbcafe.member.entity.User;
import lombok.Data;

@Data
public class SignupDto {
    private String username;
    private String password;
    private String email;
    private String name;
    private String gender;
    private String phone;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .gender(gender)
                .phone(phone)
                .stamp(0)

                .build();
    }
}