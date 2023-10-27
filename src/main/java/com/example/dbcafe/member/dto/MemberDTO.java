package com.example.dbcafe.member.dto;

import com.example.dbcafe.member.entity.MemberEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class MemberDTO {
    private Long id;
    private String memberID;
    private String memberPassword;
    private String memberEmail;
    private String memberName;
    private String memberGender;
    private String role;
    private int memberPhone;


    public static MemberDTO toMemberDTO(MemberEntity memberEntity){
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberID(memberEntity.getMemberID());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberGender(memberEntity.getMemberGender());
        memberDTO.setMemberPhone(memberEntity.getMemberPhone());
        memberDTO.setRole(memberEntity.getRole());

        return memberDTO;
    }
}
