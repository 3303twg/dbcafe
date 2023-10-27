package com.example.dbcafe.member.config.auth;

import com.example.dbcafe.member.config.auth.PrincipalDetails;
import com.example.dbcafe.member.entity.MemberEntity;
import com.example.dbcafe.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<MemberEntity> memberEntity = memberRepository.findByMemberID(username);


        if(memberEntity.isPresent()) {
            MemberEntity userEntity=memberEntity.get();
            return new PrincipalDetails(userEntity);
        }
        else {
            return null;

        }
    }
}