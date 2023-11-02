package com.example.dbcafe.member.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 주문한 사용자

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "order_menu",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    private List<MenuEntity> menuItems; // 주문한 메뉴 목록

    @Column
    private int totalPrice; // 주문의 총 가격

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime orderDate; // 주문 일자

    @Column
    private String productNames;

    // 다른 필드 (주문 번호, 주소, 상태 등) 추가 가능


    // Getter 메서드

    public Long getId() {
        return id;
    }

    public int getTotalPrice(){
        return totalPrice;
    }

    public List<MenuEntity> getMenuItems(){
        return menuItems;
    }

    public String getproductNames(){
        return productNames;
    }

    public LocalDateTime getOrderDate(){
        return orderDate;
    }

    public Long getUserId() {
        if (user != null) {
            return user.getId();
        }
        return null; // 또는 원하는 기본값
    }




    // 생성된 Setter 메서드들
    public void setUser(User user) {
        this.user = user;
    }

    public void setMenuItems(List<MenuEntity> menuItems) {
        this.menuItems = menuItems;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setProductNames(String productNames) {
        this.productNames = productNames;
    }

    public String getProductNames() {
        return this.productNames;
    }

//    public String getProductNames() {
//
//        return productNames;
//    }

}