package com.example.dbcafe.member.Service;

import com.example.dbcafe.member.entity.CartEntity;
import com.example.dbcafe.member.entity.User;
import com.example.dbcafe.member.entity.MenuEntity;
import com.example.dbcafe.member.repository.CartRepositoty;
import com.example.dbcafe.member.repository.UserRepository;
import com.example.dbcafe.member.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class CartService {
    private final UserRepository userRepository;

    @Autowired
    private CartRepositoty cartRepository;
    @Autowired
    private MenuRepository menuRepository;


    // 장바구니에 메뉴 추가 로직
    public void addToCart(Long Id, Long menuId) {
        User user = userRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        CartEntity cart = user.getCart();

        if (cart == null) {
            cart = new CartEntity();
            cart.setUser(user);
        }

        MenuEntity menu = menuRepository.findById(menuId).orElseThrow(() -> new EntityNotFoundException("Menu not found"));
        List<MenuEntity> menuItems = cart.getMenuItems();

        if (menuItems == null) {
            menuItems = new ArrayList<>();
        }

        menuItems.add(menu);
        cart.setMenuItems(menuItems);

        int newTotalPrice = calculateTotalPrice(cart);
        cart.setTotalPrice(newTotalPrice);

        cartRepository.save(cart);
    }


    // 장바구니에서 메뉴 제거 로직
    public void removeItem(Long cartId, List<Long> itemIndex) {
        CartEntity cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null) {
            List<MenuEntity> menuItems = cart.getMenuItems();

            int testnum = 1;

            for (Long index : itemIndex) {
                int int_index = index.intValue();

                menuItems.remove(int_index-testnum);

                cart.setMenuItems(menuItems);

                // 장바구니의 총 가격 업데이트
                int newTotalPrice = calculateTotalPrice(cart);
                cart.setTotalPrice(newTotalPrice);

                // 장바구니 엔티티 저장
                cartRepository.save(cart);
                testnum ++;
            }
        }
    }





    private int calculateTotalPrice(CartEntity cart) {
        List<MenuEntity> menuItems = cart.getMenuItems();
        int totalPrice = 0;

        for (MenuEntity menu : menuItems) {
            int menuPrice = menu.getPrice();
            totalPrice = totalPrice + menuPrice;
        }

        return totalPrice;
    }

}
