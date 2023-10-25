package com.example.dbcafe.member.controller;

import com.example.dbcafe.member.Service.CartService;
import com.example.dbcafe.member.Service.MenuService;
import com.example.dbcafe.member.dto.BoardDTO;
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

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;



    @PostMapping("/{cartId}/add/{menuId}")
    public ResponseEntity<String> addToCart(@PathVariable Long cartId, Long id, Model model, @PathVariable Long menuId) {
        cartService.addToCart(cartId, menuId);



        return ResponseEntity.ok("메뉴가 장바구니에 추가되었습니다.");
    }

    @PostMapping("/{cartId}/remove/{menuId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long cartId, @PathVariable Long menuId) {
        cartService.removeFromCart(cartId, menuId);
        return ResponseEntity.ok("장바구니에서 메뉴가 제거되었습니다.");
    }



}
