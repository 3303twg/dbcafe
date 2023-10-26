package com.example.dbcafe.member.config.auth;

import com.example.dbcafe.member.entity.MemberEntity;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private MemberEntity user;

    public PrincipalDetails(MemberEntity user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collector = new ArrayList<>();
        collector.add(() -> { return user.getRole();}); // 람다식

        return collector;
    }


    @Override
    public String getPassword() {
        return user.getMemberPassword();
    }

    @Override
    public String getUsername() {
        return user.getMemberID();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}