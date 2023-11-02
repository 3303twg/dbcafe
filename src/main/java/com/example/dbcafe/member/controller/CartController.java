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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    @Autowired
    private CartRepositoty cartRepositoty;
    @Autowired
    private UserInfoService userInfoService;




    //장바구니넣는기능
    @PostMapping("/cart/{cartId}/add/{menuId}")
    public String  addToCart(@PathVariable Long cartId, @PathVariable Long menuId,
                             Principal principal) {
        cartService.addToCart(cartId, menuId);

        String username = (String) principal.getName();
        Long loggedInUser = userInfoService.getUserIdByUsername(username);

        if (loggedInUser == null) {
            return "login";
        }

        return "redirect:/menu/list";
    }


    //장바구니빼는기능
//    @GetMapping("/cart/{cartId}/remove/{menuId}")
//    public String removeFromCart(@PathVariable Long cartId, @PathVariable Long menuId) {
//        cartService.removeItem(cartId, menuId,1);
//        return "redirect:/menu/list";
//    }




    @GetMapping("/cart/list/{cartId}") //이거 유저아이디로 변경해야할듯??
    public String CartList(@PathVariable Long cartId, Model model, HttpSession session, Principal principal){



        //유저의 아이디를 가져옴
        String username = (String) principal.getName();
        Long loggedInUser = userInfoService.getUserIdByUsername(username);


        //여기수정해야할듯 유저아디를받아서 카트번호로 교체후 그거로 검색해야할건데
        CartEntity cart = cartRepositoty.findById(cartId).orElse(null);

//        CartEntity cart = cartRepositoty.findByUser(user); // 해당 사용자의 장바구니를 찾습니다.

        if (cart != null) {
            model.addAttribute("cartItems", cart.getMenuItems());
            model.addAttribute("totalPrice", cart.getTotalPrice());
            model.addAttribute("loginUser", loggedInUser);

        }

        //return "basket";
        return "cartDetail";
    }


}
