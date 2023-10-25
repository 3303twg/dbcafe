package com.example.dbcafe.member.controller;

import com.example.dbcafe.member.Service.CartService;
import com.example.dbcafe.member.Service.MenuService;
import com.example.dbcafe.member.dto.BoardDTO;
import com.example.dbcafe.member.dto.MemberDTO;
import com.example.dbcafe.member.dto.MenuDTO;
import com.example.dbcafe.member.repository.CartRepositoty;
import com.example.dbcafe.member.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;



    //장바구니넣는기능
    @PostMapping("/{cartId}/add/{menuId}")
    public String  addToCart(@PathVariable Long cartId, @PathVariable Long menuId, Model model, HttpSession session) {
        cartService.addToCart(cartId, menuId);

        String loggedInUser = (String) session.getAttribute("loginUser");
        //하 모르겠ㄱ다
        if (loggedInUser == null) {
            return "login";
        }

        return "redirect:/menu/list";
    }


    //장바구니빼는기능
    @PostMapping("/cart/{cartId}/remove/{menuId}")
    public String removeFromCart(@PathVariable Long cartId, @PathVariable Long menuId) {
        cartService.removeFromCart(cartId, menuId);
        return "redirect:/menu/list";
    }



}
