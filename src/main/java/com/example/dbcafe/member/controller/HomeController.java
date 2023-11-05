package com.example.dbcafe.member.controller;

import com.example.dbcafe.member.Service.BoardService;
import com.example.dbcafe.member.dto.BoardDTO;
import com.example.dbcafe.member.entity.BoardEntitiy;
import com.example.dbcafe.member.entity.OrderEntity;
import com.example.dbcafe.member.entity.User;
import com.example.dbcafe.member.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Key;
import java.security.Principal;
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
    public String payment(HttpSession session, Model model){return "payment";}

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


    @Autowired
    private OrderRepository orderRepository;

    private OrderEntity orderEntity;
    @GetMapping("/order")
    public String listOrdersForUser(Model model, Principal principal) {

        List<OrderEntity> userOrders = orderRepository.findAll();
        model.addAttribute("orders", userOrders);

        int totaltotalprice=0;
        for (OrderEntity order : userOrders) {
            totaltotalprice = totaltotalprice+order.getTotalPrice();
        }

        model.addAttribute("totaltotalprice", totaltotalprice);

        return"orders";
    }

}
