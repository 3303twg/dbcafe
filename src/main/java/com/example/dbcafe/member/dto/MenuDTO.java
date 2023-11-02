package com.example.dbcafe.member.dto;

import com.example.dbcafe.member.entity.MenuEntity;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MenuDTO {
    private Long id;

    private String menuName;

    private int menuPrice; //String >> BigDecimal로 수정했음

    private String menuImagePath;

    private Long menuType;

    public static MenuDTO toDTO(MenuEntity menuEntity){
        MenuDTO menuDTO= new MenuDTO();
        menuDTO.setId(menuEntity.getId());
        menuDTO.setMenuName(menuEntity.getMenuName());
        menuDTO.setMenuPrice(menuEntity.getMenuPrice());
        menuDTO.setMenuType(menuEntity.getMenuType());
        menuDTO.setMenuImagePath(menuEntity.getMenuImagePath());

        return menuDTO;
    }

}
