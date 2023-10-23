package com.example.dbcafe.member.controller;

import com.example.dbcafe.member.Service.BoardService;
import com.example.dbcafe.member.dto.BoardDTO;
import com.example.dbcafe.member.entity.BoardEntitiy;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Key;
import java.util.List;
import java.util.stream.Collectors;

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
    public String payment(){
        return "payment";
    }

    @GetMapping("/text")
    public String text(){
        return "text";
    }


    @GetMapping("/brand")
    public String brand(){
        return "brand";
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



}
