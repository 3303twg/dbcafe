package com.example.dbcafe.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class HomeController {

    @GetMapping("/")
    public String index(){
        return "main";
    }


    @GetMapping("/loginn")
    public String loginn(){return "loginn"; }

    @GetMapping("/test/test")
    public String test(){
        return "test";
    }

    @GetMapping("/test2")
    public String test2(){
        return "test2";
    }

    @GetMapping("/payment")
    public String payment(HttpSession session, Model model){return "payment";}

    @GetMapping("/brand")
    public String brand(){
        return "ABOUTUS";
    }

    @GetMapping("/store")
    public String store(){
        return "store";
    }

    @GetMapping("/partnership")
    public String partnership(){
        return "partnership";
    }

    @GetMapping("/partnership/write")
    public String partnership_write(){
        return "partnership_write";
    }

    @GetMapping("/cart")
    public String cart(){
        return "cart";
    }

    @GetMapping("/cart/test")
    public String testcart(){
        return "basket";
    }

    @GetMapping("/MYQnA") // << IP/MYQ&A 와같이 매핑됨
    public String myqna(){return "Q&A_write";} // << 리턴해줄 페이지의 명칭

    @GetMapping("/MYQnAre") // << IP/MYQ&A 와같이 매핑됨
    public String myqnam(){return "Q&A_write_modify";} // << 리턴해줄 페이지의 명칭







}
