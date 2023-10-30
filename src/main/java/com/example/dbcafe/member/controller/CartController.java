package com.example.dbcafe.member.controller;

import com.example.dbcafe.member.Service.CartService;
import com.example.dbcafe.member.Service.MenuService;
import com.example.dbcafe.member.Service.UserInfoService;
import com.example.dbcafe.member.dto.BoardDTO;
import com.example.dbcafe.member.dto.SignupDto;
import com.example.dbcafe.member.dto.MenuDTO;
import com.example.dbcafe.member.entity.CartEntity;
import com.example.dbcafe.member.entity.User;
import com.example.dbcafe.member.repository.CartRepositoty;
import com.example.dbcafe.member.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepositoty cartRepositoty;

    @Autowired
    private UserInfoService userInfoService;



    //장바구니넣는기능
    @PostMapping("/cart/{cartId}/add/{menuId}")
    public String  addToCart(@PathVariable Long cartId, @PathVariable Long menuId, Model model, HttpSession session, Principal principal) {
        cartService.addToCart(cartId, menuId);


        String username = (String) principal.getName();
        Long loggedInUser = userInfoService.getUserIdByUsername(username);

        //하 모르겠ㄱ다
        if (loggedInUser == null) {
            return "login";
        }

        return "redirect:/menu/list";
    }


    //장바구니빼는기능
    @GetMapping("/cart/{cartId}/remove/{menuId}")
    public String removeFromCart(@PathVariable Long cartId, @PathVariable Long menuId) {
        cartService.removeItem(cartId, menuId,1);
        return "redirect:/menu/list";
    }


    @GetMapping("/cart/list/{cartId}")
    public String CartList(@PathVariable Long cartId, Model model, HttpSession session, Principal principal){


        String username = (String) principal.getName();
        Long loggedInUser = userInfoService.getUserIdByUsername(username);


        User user_entity = (User) session.getAttribute("user_entity");

        User user = user_entity; // 현재 로그인한 사용자를 가져오는 코드

        CartEntity cart = cartRepositoty.findById(cartId).orElse(null);

//        CartEntity cart = cartRepositoty.findByUser(user); // 해당 사용자의 장바구니를 찾습니다.

        if (cart != null) {
            model.addAttribute("cartItems", cart.getMenuItems());
            model.addAttribute("totalPrice", cart.getTotalPrice());
            model.addAttribute("loginUser", loggedInUser);

        }

        return "basket";
        //return "cartDetail";



    }


}
