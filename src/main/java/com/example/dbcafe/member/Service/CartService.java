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
        //유저id를 통해 해당유저의 카트를 가져옴
        User user = userRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        CartEntity cart = user.getCart();

        if (cart == null) {//만약 카트가 없을경우 카트를 새로생성후 할당해줌
            cart = new CartEntity();
            cart.setUser(user);
        }

        //메뉴id값을통해 해당메뉴를찾아 엔티티값을 가져옴
        MenuEntity menu = menuRepository.findById(menuId).orElseThrow(() -> new EntityNotFoundException("Menu not found"));
        List<MenuEntity> menuItems = cart.getMenuItems(); //기존카트에 아이템이 존재할경우 리스트에 넣어줌

        if (menuItems == null) { //리스트가 없을경우 새로생성
            menuItems = new ArrayList<>();
        }
        //리스트에 장바구니에 담을아이템을 추가해줌
        menuItems.add(menu);
        cart.setMenuItems(menuItems); //리스트를 카트에 할당

        //메뉴들의 가격을 설정함
        int newTotalPrice = calculateTotalPrice(cart);
        cart.setTotalPrice(newTotalPrice);
        //장바구니 저장
        cartRepository.save(cart);
    }


    // 장바구니에서 메뉴 제거 로직
    public void removeItem(Long userId, List<Long> itemIndex) {
        //유저id를통해 해당유저의 카트를 가져옴
        CartEntity cart = cartRepository.findByUserId(userId);
        if (cart != null) {//카트가 존재할경우 아이템리스트를 가져옴
            List<MenuEntity> menuItems = cart.getMenuItems();

            int testnum = 1;//반복하며 삭제할경우 줄어든 리스트의 크기를 보정하기위한 숫자

            //리스트의 요소의갯수만큼 반복
            for (Long index : itemIndex) {
                int int_index = index.intValue(); //Long타입을 int로 변환
                //해당 순번의 아이템을 리스트에서 제거
                menuItems.remove(int_index-testnum);

                testnum ++;
            }
            //반복이 종료된후 남아있는 리스트를 장바구니에할당
            cart.setMenuItems(menuItems);

            // 장바구니의 총 가격 업데이트
            int newTotalPrice = calculateTotalPrice(cart);
            cart.setTotalPrice(newTotalPrice);

            // 장바구니 엔티티 저장
            cartRepository.save(cart);
        }
    }




    //장바구니 가격 계산
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
