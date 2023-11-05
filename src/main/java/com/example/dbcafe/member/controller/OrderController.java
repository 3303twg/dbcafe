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

@RestController    //html에서 restapi형식으로 사용하기위한 컨트롤러
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CartService cartService;


    //주문을 생성하기위한 컨트롤러
    @PostMapping("/createOrder")
    public String createOrder(Long userId, boolean useCoupon) {
        try {//쿠폰사용이 체크된상태로 결제했을경우
            if(useCoupon == true) {
                userInfoService.usecoupon(userId); //쿠폰을 사용하는 서비스
            }       //주문을 생성하는 서비스
            return orderService.createOrder(userId).getProductNames();
        } catch (Exception e) {
            // 주문 생성에 실패한 경우에 대한 처리
            e.printStackTrace();
            return null; // 또는 예외 처리에 따른 응답을 반환
        }
    }

    //주문이 끝난후의 처리를 하는 컨트롤러
    @PostMapping("/endOrder")
    public String del_cart(Long userId){
        userInfoService.addStamp(userId); //해당유저를찾아 스탬프를 더해줌
        orderService.del_cart(userId);  //해당유저의 카트초기화
        return null;
    }

    //UID생성용 컨트롤러
    @PostMapping("/createUUID")
    public String createUUID(){
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }


}