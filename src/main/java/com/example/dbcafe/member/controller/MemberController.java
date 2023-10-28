package com.example.dbcafe.member.controller;

import com.example.dbcafe.member.Service.MemberService;
import com.example.dbcafe.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;



@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

//    @GetMapping("/register")
//    public String register(){return "register";}
    @GetMapping("/test/save")
    public String saveForm(){
        return "save";
    }

//    @PostMapping("/test/save")
//    public String save(@ModelAttribute MemberDTO memberDTO){
//
//        memberService.save(memberDTO);
//
//        return "login";
//    }

    @GetMapping("/test/login")
    public String loginForm(){
        return "login";
    }

//    @PostMapping("/test/login")
//    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session, Model model){
//
//
//        MemberDTO loginResult = memberService.login(memberDTO);
//
//        if(loginResult != null){
//
////            session.setAttribute("loginEmail",loginResult.getMemberEmail());//세션에 사용자e메일저장
//
//            session.setAttribute("loginUser", loginResult.getMemberID()); //.getMemberID만빼면 DTO를 다넣게됨
//            session.setAttribute("user_role", loginResult.getRole());
//
//            sessionList.put(session.getId(), session);
//            session.setMaxInactiveInterval(600 * 50);
//
//            return "main";
//        }
//        else{
//            return "login";
//        }
//    }


    @GetMapping("/test/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

    @GetMapping("/test/list")
    @ResponseBody
    public Map<String, String> sessionList() {
        Enumeration elements = sessionList.elements();
        Map<String, String> lists = new HashMap<>();
        while(elements.hasMoreElements()) {
            HttpSession session = (HttpSession)elements.nextElement();
            lists.put(session.getId(), String.valueOf(session.getAttribute("userId")));
        }
        return lists;
    }


    public static Hashtable sessionList = new Hashtable();
}
