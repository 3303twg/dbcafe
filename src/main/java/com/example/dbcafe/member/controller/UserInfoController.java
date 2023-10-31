package com.example.dbcafe.member.controller;

import com.example.dbcafe.member.Service.OrderService;
import com.example.dbcafe.member.Service.UserInfoService;
import com.example.dbcafe.member.config.auth.PrincipalDetails;
import com.example.dbcafe.member.entity.OrderEntity;
import com.example.dbcafe.member.entity.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;

    private final OrderService orderService;


    @GetMapping("/mypage")
    public String mypage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        User user = principalDetails.getUser();
        model.addAttribute("userInfo",user);
        return "mypage";}

    @GetMapping("/mypage/qna")
    public String myQnA(){
        return "MyQnA";
    }

//    @GetMapping("mypage/order")
//    public String myOrder(){
//        return"MyOrderr";
//    }

    @GetMapping("/mypage/order/{userId}")
    public String listOrdersForUser(@PathVariable Long userId, Model model) {
        List<OrderEntity> userOrders = orderService.getOrdersForUser(userId); // 해당 유저의 주문 데이터를 가져오는 메서드를 구현해야 합니다.
        model.addAttribute("orders", userOrders);
        return"MyOrderr";
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

