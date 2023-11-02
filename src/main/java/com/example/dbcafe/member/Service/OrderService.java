package com.example.dbcafe.member.Service;

import com.example.dbcafe.member.entity.CartEntity;
import com.example.dbcafe.member.entity.MenuEntity;
import com.example.dbcafe.member.entity.OrderEntity;
import com.example.dbcafe.member.entity.User;
import com.example.dbcafe.member.repository.CartRepositoty;
import com.example.dbcafe.member.repository.OrderRepository;
import com.example.dbcafe.member.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private UserRepository memberRepository; // 사용자 정보를 가져오기 위한 리포지토리
    @Autowired
    private CartRepositoty cartRepository; // 사용자의 장바구니 정보를 가져오기 위한 리포지토리
    @Autowired
    private OrderRepository orderRepository; // 주문 정보를 저장하기 위한 리포지토리

    public OrderEntity createOrder(Long userId) { //유저고유의 id값 즉 .getID()임
        // 사용자 정보 가져오기
        User user = memberRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // 사용자의 장바구니 정보 가져오기
        CartEntity cart = user.getCart();

        if (cart == null || cart.getMenuItems().isEmpty()) {
            throw new IllegalArgumentException("장바구니가 비어 있습니다.");
        }

        // 장바구니에 있는 메뉴 목록 가져오기
        List<MenuEntity> menuItems = cart.getMenuItems();

        // 총 주문 가격 계산
        int totalOrderPrice = calculateTotalOrderPrice(menuItems);

        // 주문 생성
        OrderEntity order = new OrderEntity();
        order.setUser(user);
        order.setMenuItems(new ArrayList<>(menuItems));
        order.setTotalPrice(totalOrderPrice);

        // 동적으로 상품명 설정
        StringBuilder productNameBuilder = new StringBuilder();
        for (MenuEntity menuItem : menuItems) {
            productNameBuilder.append(menuItem.getName()).append(", ");
        }
        order.setProductNames(productNameBuilder.toString());

        // 주문 정보 저장
        orderRepository.save(order);

        return order;
    }

    //장바구니비우기
    public void del_cart(Long userId){

        User user = memberRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        CartEntity cart = user.getCart();
        //         주문 생성 후 장바구니 비우기
        cart.setMenuItems(new ArrayList<>());
        cart.setTotalPrice(0);
        cartRepository.save(cart);
    }

    private int calculateTotalOrderPrice(List<MenuEntity> menuItems) {
        int totalPrice = 0;

        for (MenuEntity menu : menuItems) {
            int menuPrice = menu.getPrice();
            totalPrice = totalPrice+menuPrice;
        }

        return totalPrice;
    }

    public List<OrderEntity> getOrdersForUser(Long userId) {
        // OrderRepository를 사용하여 유저 ID를 기반으로 주문 목록을 가져옵니다.
        List<OrderEntity> userOrders = orderRepository.findByUser_Id(userId);
        return userOrders;
    }
}