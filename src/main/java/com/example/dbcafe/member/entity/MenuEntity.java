package com.example.dbcafe.member.entity;

import com.example.dbcafe.member.dto.MenuDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "menu_table")
public class MenuEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String menuName;
//    @Column
//    private String menuPrice; //조금만 수정할겡

    @Column
    private int menuPrice;

    @Column
    private String menuImagePath;
    @Column
    private Long menuType;

    public int getPrice() {
        return menuPrice;
    }

    public static MenuEntity toMenuEntity(MenuDTO menuDTO){
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.setId(menuEntity.getId());
        menuEntity.setMenuName(menuDTO.getMenuName());
        menuEntity.setMenuPrice(menuDTO.getMenuPrice());
        menuEntity.setMenuImagePath(menuDTO.getMenuImagePath());
        menuEntity.setMenuType(menuDTO.getMenuType());
        return menuEntity;
    }

    public String getName() {
        return menuName;
    }
}
