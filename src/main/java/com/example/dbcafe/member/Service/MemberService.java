package com.example.dbcafe.member.Service;

import com.example.dbcafe.member.dto.MemberDTO;
import com.example.dbcafe.member.entity.MemberEntity;
import com.example.dbcafe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class MemberService {
    private final MemberRepository memberRepository;
    public void save(MemberDTO memberDTO) {
        memberDTO.setRole("ROLE_USER");

        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);


    }

    public MemberDTO login(MemberDTO memberDTO) {

        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (byMemberEmail.isPresent()) {
            MemberEntity memberEntity = byMemberEmail.get();
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {

                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);

                return dto;
            }
            else {
                return null;
            }
        }
        else{
            return null;
            }

    }



}
