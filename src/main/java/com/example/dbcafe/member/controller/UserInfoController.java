package com.example.dbcafe.member.controller;

import com.example.dbcafe.member.config.auth.PrincipalDetails;
import com.example.dbcafe.member.entity.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserInfoController {
    @GetMapping("/mypage")
    public String mypage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        User user = principalDetails.getUser();
        model.addAttribute("userInfo",user);

        return "mypage";}

}

