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
    public String  addToCart(@PathVariable Long cartId, @PathVariable Long menuId, Principal principal) {
        //로그인상태 받아오기
        String username = (String) principal.getName();
        Long loggedInUser = userInfoService.getUserIdByUsername(username);

        //비로그인시 로그인창으로 보냄
        if (loggedInUser == null) {
            return "login";
        }
        else{//로그인상태일때만 카트에 아이템을 추가함
            cartService.addToCart(cartId, menuId);
        }

        return "redirect:/menu/list";
    }


    //장바구니빼는기능
//    @GetMapping("/cart/{cartId}/remove/{menuId}")
//    public String removeFromCart(@PathVariable Long cartId, @PathVariable Long menuId) {
//        cartService.removeItem(cartId, menuId,1);
//        return "redirect:/menu/list";
//    }




    //장바구니 조회
    @GetMapping("/cart/list")
    public String CartList(Model model, HttpSession session, Principal principal){
        //비로그인시 로그인창으로 보냄
        if (principal == null) {
            return "login";
        }

        //유저의 아이디를 가져옴
        String username = (String) principal.getName();
        Long loggedInUser = userInfoService.getUserIdByUsername(username);


        //유저id값을통해 유저의 cart를찾아 엔티티를 받아옴
        CartEntity cart = cartRepositoty.findByUserId(loggedInUser);

//        CartEntity cart = cartRepositoty.findByUser(user); // 해당 사용자의 장바구니를 찾습니다.

        if (cart != null) {
            //카트가 있을경우 html에 사용하기위해 모델에 올림
            model.addAttribute("cartItems", cart.getMenuItems());
            model.addAttribute("totalPrice", cart.getTotalPrice());
            model.addAttribute("loginUser", loggedInUser);

        }

        //return "basket";
        return "cartDetail";
    }


}
