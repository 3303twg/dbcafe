package com.example.dbcafe.member.controller;

import com.example.dbcafe.member.config.auth.PrincipalDetails;
import com.example.dbcafe.member.entity.User;
import com.example.dbcafe.member.Service.AuthService;
import com.example.dbcafe.member.dto.SignupDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    @GetMapping("/signin")
    public String SigninForm() {
        return "login";
    }

    @GetMapping("/signup")
    public String SignupForm() {
        return "register";
    }


    @PostMapping("/signterm")
    public String term(User user, Model model) {
        User userEntity = authService.signup(user);
        System.out.println(userEntity);

        return "redirect:/signin";
    }

//    @PostMapping("/signterm")
//    public String term(User user, Model model){
//        User newUser = user;
//        model.addAttribute("user",newUser);
//        return "REGISTER_TERMS";}
//
//    @PostMapping("/signup")
//    public String signUp(Model model) {
//        User user = (User) model.getAttribute("user");
////        User newUser = user; //새로운 유저 받음
//
//        User userEntity = authService.signup(user);
//        System.out.println(userEntity);
//
//        return "redirect:/signin";
//    }

    // 로그인성공 창에서 로그아웃 버튼
    //@RequestMapping(value="logout", method = RequestMethod.GET)
    @GetMapping("/logout")
    public String logout(HttpSession session) throws Exception {
        authService.logout(session);
        return "redirect:/";
    }


}