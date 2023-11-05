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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private UserRepository memberRepository; // 사용자정보를 가져오기위한 리포지토리
    @Autowired
    private CartRepositoty cartRepository; // 사용자의 장바구니 정보를 가져오기위한 리포지토리
    @Autowired
    private OrderRepository orderRepository; // 주문정보를 저장하기위한 리포지토리

    public OrderEntity createOrder(Long userId) { //유저고유의 id값 즉 .getID()임
        //id값을 통해 사용자 정보를 가져옴
        User user = memberRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        //해당 유저의 장바구니엔티티를 가져옴
        CartEntity cart = user.getCart();


        // 장바구니에 있는 메뉴 리스트 가져옴
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


        LocalDateTime orderTime = LocalDateTime.now();
        order.setOrderDate(orderTime);

        // 주문 정보 저장
        orderRepository.save(order);

        return order;
    }

    //장바구니비우기
    public void del_cart(Long userId){

        //id값을 사용해 유저의 엔티티를 가져옴
        User user = memberRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        //해당유저의 카트정보를 가져옴
        CartEntity cart = user.getCart();
        if (cart == null) { //없을경우 새로생성
            cart = new CartEntity();
            cart.setUser(user);
        }
        //주문이 끝났으니 장바구니 비워줌
        cart.setMenuItems(new ArrayList<>());
        cart.setTotalPrice(0); //가격초기화
        cartRepository.save(cart);
    }

    //가격계산
    private int calculateTotalOrderPrice(List<MenuEntity> menuItems) {
        int totalPrice = 0;

        for (MenuEntity menu : menuItems) {
            int menuPrice = menu.getPrice();
            totalPrice = totalPrice+menuPrice;
        }

        return totalPrice;
    }

    //유저의 주문내역엔티티를 리스트로 받아옴
    public List<OrderEntity> getOrdersForUser(Long userId) {
        // 유저id를통해 주문내역엔티티를 모두 리스트로 가져옴
        List<OrderEntity> userOrders = orderRepository.findByUser_Id(userId);
        return userOrders;
    }
}