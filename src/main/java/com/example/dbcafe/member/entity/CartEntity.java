package com.example.dbcafe.member.entity;

import lombok.*;
import com.example.dbcafe.member.entity.User;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user; // 해당 장바구니를 소유한 사용자

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "cart_menu",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    private List<MenuEntity> menuItems; // 장바구니에 담긴 메뉴 목록

    @Column
    private int totalPrice; // 장바구니의 총 가격

    public int getTotalPrice(){
        return totalPrice;
    }
    // 다른 필드는 필요한 경우 추가

    public List<MenuEntity> getMenuItems() {
        return menuItems;
    }


}
