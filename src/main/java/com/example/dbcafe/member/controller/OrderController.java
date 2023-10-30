package com.example.dbcafe.member.controller;

import com.example.dbcafe.member.Service.OrderService;
import com.example.dbcafe.member.Service.UserInfoService;
import com.example.dbcafe.member.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    private UserInfoService userInfoService;

    @PostMapping("/createOrder")
    public String createOrder(Long userId) {
        try {       //리턴타입이 OrderEntity타입임
            return orderService.createOrder(userId).getProductNames();
        } catch (Exception e) {
            // 주문 생성에 실패한 경우에 대한 처리
            e.printStackTrace();
            return null; // 또는 예외 처리에 따른 응답을 반환
        }
    }

    @PostMapping("/endOrder")
    public String del_cart(Long userId){
        orderService.del_cart(userId);
        return null;
    }

    @PostMapping("/createUUID")
    public String createUUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}