package com.example.dbcafe.member.controller;

import com.example.dbcafe.member.Service.CartService;
import com.example.dbcafe.member.Service.OrderService;
import com.example.dbcafe.member.Service.UserInfoService;
import com.example.dbcafe.member.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CartService cartService;


    @PostMapping("/createOrder")
    public String createOrder(Long userId, boolean useCoupon) {
        try {       //리턴타입이 OrderEntity타입임
            if(useCoupon == true) {
                userInfoService.usecoupon(userId);
            }
            return orderService.createOrder(userId).getProductNames();
        } catch (Exception e) {
            // 주문 생성에 실패한 경우에 대한 처리
            e.printStackTrace();
            return null; // 또는 예외 처리에 따른 응답을 반환
        }
    }

    @PostMapping("/endOrder")
    public String del_cart(Long userId){
        userInfoService.addStamp(userId, 10);
        orderService.del_cart(userId);
        return null;
    }

    @PostMapping("/createUUID")
    public String createUUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }


}