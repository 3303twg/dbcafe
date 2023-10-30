package com.example.dbcafe.member.controller;

import com.example.dbcafe.member.Service.UserInfoService;
import com.example.dbcafe.member.config.auth.PrincipalDetails;
import com.example.dbcafe.member.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;
    @GetMapping("/mypage")
    public String mypage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        User user = principalDetails.getUser();
        model.addAttribute("userInfo",user);
        return "mypage";}

    @GetMapping("/mypage/qna")
    public String myQnA(){
        return "MyQnA";
    }

    @GetMapping("mypage/order")
    public String myOrder(){
        return"MyOrder";
    }

    @GetMapping("/mypage/update")
    public String infoModify(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        User user = principalDetails.getUser();
        model.addAttribute("userInfo",user);
        return "information";
    }
    @PostMapping("/mypage/update")
    public String infoUpdate(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        User usertemp = userInfoService.getUserByUsername(principalDetails.getUser().getUsername());


        return "massage";
    }

}

