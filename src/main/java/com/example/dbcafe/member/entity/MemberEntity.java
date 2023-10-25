package com.example.dbcafe.member.entity;

import com.example.dbcafe.member.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String memberID;

    @Column
    private String MemberPassword;

    @Column(unique = true)
    private String memberEmail;

    @Column
    private String MemberName;

    @Column
    private String MemberGender;

    @Column
    private int MemberPhone;

    @OneToOne(mappedBy = "user")
    private CartEntity cart; // 사용자와의 관계

    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberID(memberDTO.getMemberID());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberName((memberDTO.getMemberName()));
        memberEntity.setMemberGender(memberDTO.getMemberGender());
        memberEntity.setMemberPhone(memberDTO.getMemberPhone());
        return memberEntity;
    }

    public CartEntity getCart() {
        return cart;
    }

}
