package com.example.dbcafe.member.entity;

import com.example.dbcafe.member.dto.SignupDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity // DB에 테이블 자동 생성
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) // username 중목 안됨
    private String username;
    private String password;
    private String name;
    private String email;
    private String gender;
    private String phone;

    private String role; // 권한

    private LocalDateTime createDate; // 날짜

    private int stamp; //스템프 값을 저장할부분

    @OneToOne(mappedBy = "user")
    private CartEntity cart;
    public CartEntity getCart() {
        return cart;
    }

    public String getUsername(){
        return username;
    }

    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    public SignupDto toDto(){
        return SignupDto.builder()
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