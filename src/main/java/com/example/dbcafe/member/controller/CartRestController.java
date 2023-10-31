package com.example.dbcafe.member.controller;

import com.example.dbcafe.member.Service.CartService;
import com.example.dbcafe.member.Service.UserInfoService;
import com.example.dbcafe.member.repository.CartRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;


@RestController
public class CartRestController {
    @Autowired
    private CartService cartService;

    @Autowired
    private CartRepositoty cartRepositoty;

    @Autowired
    private UserInfoService userInfoService;

    // 선택한 메뉴 항목을 삭제하는 컨트롤러 메서드
    @PostMapping("/removeSelectedItems")
    public ResponseEntity<String> removeSelectedItems(@RequestBody List<Long> selectedItems, Principal principal) {
        try {
            String username = principal.getName();
            Long loggedInUser = userInfoService.getUserIdByUsername(username);

            for (Long selectedItem : selectedItems) {
                cartService.removeItem(loggedInUser, selectedItem);
            }

            return new ResponseEntity<>("선택한 메뉴 삭제 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("선택한 메뉴 삭제 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
