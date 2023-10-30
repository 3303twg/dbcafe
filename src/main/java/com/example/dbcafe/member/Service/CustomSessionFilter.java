package com.example.dbcafe.member.Service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomSessionFilter extends UsernamePasswordAuthenticationFilter {

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);

        CustomAuthentication customAuth = (CustomAuthentication) SecurityContextHolder.getContext().getAuthentication();
        Long userId = customAuth.getUserId();
        String usera = customAuth.getName();
        request.getSession().setAttribute("loginUse", userId);
        request.getSession().setAttribute("loginUser", usera);
    }
}