package com.example.dbcafe.member.Service;

import com.example.dbcafe.member.dto.MemberDTO;
import com.example.dbcafe.member.entity.MemberEntity;
import com.example.dbcafe.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public void save(MemberDTO memberDTO) {
        memberDTO.setRole("ROLE_USER");
        String rawPassword= memberDTO.getMemberPassword();
        String encPassword= bCryptPasswordEncoder.encode(rawPassword);
        memberDTO.setMemberPassword(encPassword);

        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);


    }

    public MemberDTO login(MemberDTO memberDTO) {

        Optional<MemberEntity> byMemberID = memberRepository.findByMemberID(memberDTO.getMemberID());
        if (byMemberID.isPresent()) {
            MemberEntity memberEntity = byMemberID.get();
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
