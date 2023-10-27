package com.example.dbcafe.member.entity;

import com.example.dbcafe.member.config.auth.PrincipalDetails;
import com.example.dbcafe.member.dto.MemberDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "member_table")
public class MemberEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String memberID;

    @Column
    private String memberPassword;

    @Column(unique = true)
    private String memberEmail;

    @Column
    private String memberName;

    @Column
    private String memberGender;

    @Column
    private int memberPhone;

    @Column
    private String role;//권한

    @OneToOne(mappedBy = "user")
    private CartEntity cart; // 사용자와의 관계

    private LocalDateTime createDate; // 날짜

    @PrePersist // DB에 INSERT 되기 직전에 실행. 즉 DB에 값을 넣으면 자동으로 실행됨
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }

    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberID(memberDTO.getMemberID());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberName((memberDTO.getMemberName()));
        memberEntity.setMemberGender(memberDTO.getMemberGender());
        memberEntity.setMemberPhone(memberDTO.getMemberPhone());
        memberEntity.setRole(memberDTO.getRole());


        return memberEntity;
    }

    public CartEntity getCart() {
        return cart;
    }

}
