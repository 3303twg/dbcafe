package com.example.dbcafe.member.Service;

import com.example.dbcafe.member.entity.CartEntity;
import com.example.dbcafe.member.entity.MenuEntity;
import com.example.dbcafe.member.repository.CartRepositoty;
import com.example.dbcafe.member.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    @Autowired
    private CartRepositoty cartRepository;
    @Autowired
    private MenuRepository menuRepository;


    // 장바구니에 메뉴 추가 로직
    public void addToCart(Long cartId, Long menuId) {
        CartEntity cart = cartRepository.findById(cartId).orElse(new CartEntity());
        MenuEntity menu = menuRepository.findById(menuId).orElse(null);

        if (menu != null) {
            List<MenuEntity> menuItems = cart.getMenuItems();

            if (menuItems == null) {
                menuItems = new ArrayList<>(); // 만약 null이면 새 리스트를 생성
            }

            menuItems.add(menu);
            cart.setMenuItems(menuItems);

            BigDecimal newTotalPrice = calculateTotalPrice(cart);
            cart.setTotalPrice(newTotalPrice);

            cartRepository.save(cart);
        }
    }

    // 장바구니에서 메뉴 제거 로직
    public void removeFromCart(Long cartId, Long menuId) {
        CartEntity cart = cartRepository.findById(cartId).orElse(null);
        if (cart != null) {
            List<MenuEntity> menuItems = cart.getMenuItems();
            menuItems.removeIf(item -> item.getId().equals(menuId));
            cart.setMenuItems(menuItems);

            BigDecimal newTotalPrice = calculateTotalPrice(cart);
            cart.setTotalPrice(newTotalPrice);

            cartRepository.save(cart);
        }
    }

    private BigDecimal calculateTotalPrice(CartEntity cart) {
        List<MenuEntity> menuItems = cart.getMenuItems();
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (MenuEntity menu : menuItems) {
            BigDecimal menuPrice = menu.getPrice();
            totalPrice = totalPrice.add(menuPrice);
        }

        return totalPrice;
    }

}
