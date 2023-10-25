package com.example.dbcafe.member.controller;

import com.example.dbcafe.member.Service.MemberService;
import com.example.dbcafe.member.Service.MenuService;
import com.example.dbcafe.member.dto.MemberDTO;
import com.example.dbcafe.member.dto.MenuDTO;
import com.example.dbcafe.member.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.http.HTTP;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/menu")
    public String menuList(){return "redirect:/menu/list";}

    @GetMapping("/menu/menuRegister")
    public String menuRegister(){return "menuRegister";}

    @PostMapping("/menu/menuRegister")
    public String menuSave(@ModelAttribute MenuDTO menuDTO , MultipartFile file ,HttpServletRequest request, Model model) throws Exception {
        menuService.register(menuDTO,file,request);

        return "redirect:/menu/list";
    }
    @GetMapping("/menu/delete")
    public String menuDelete(Long id,HttpServletRequest request){

        menuService.delete(id,request);
        return "redirect:/menu/list";

    }
    @GetMapping("/menu/list")
    public String menulist(Model model, Long typeID, HttpSession session){

        MemberDTO loggedInUser = (MemberDTO) session.getAttribute("loginUser");

//        String loggedInUser = (String) session.getAttribute("loginUser");

        if (loggedInUser != null) {
            // 사용자 정보를 Thymeleaf 모델에 추가
            model.addAttribute("loginUser", loggedInUser);
        } else {
            // 사용자가 로그인하지 않은 경우 처리
            return "login";
        }

        if(typeID == null) {
            List<MenuDTO> menuDTOList = menuService.menuFindAll();
            model.addAttribute("menuList", menuDTOList);

        }
        else{
            List<MenuDTO> menuDTOList = menuService.menufinds(typeID);
            model.addAttribute("menuList", menuDTOList);
        }
        return "menuList";
    }
    @GetMapping("/menu/modify/{id}")
    public String menumodifing(@PathVariable("id")Long id,Model model){
        MenuDTO menuDTO=menuService.menufind(id);
        model.addAttribute("menuObject",menuDTO);

        return "menuModify";
    }

    @PostMapping("/menu/modify/{id}")
    public String menumodify(@PathVariable("id") Long id,MenuDTO menuDTO,MultipartFile file,HttpServletRequest request)throws Exception{
        MenuDTO menuDTOtmp = menuService.menufind(id);
        menuDTOtmp.setId(menuDTO.getId());
        System.out.print(menuDTO);
        if(menuDTO.getMenuName()!=null){menuDTOtmp.setMenuName(menuDTO.getMenuName());
        System.out.print("chg name");}
        if(menuDTO.getMenuPrice()!=null){menuDTOtmp.setMenuPrice(menuDTO.getMenuPrice());
            System.out.print("chg price");}
        if(menuDTO.getMenuType()!=null){menuDTOtmp.setMenuType(menuDTO.getMenuType());
            System.out.print("chg type");}
        if(file!=null){menuService.delete(id,request);
            System.out.print("del img");}
        menuService.register(menuDTOtmp,file,request);
        return "redirect:/menu/list";
    }

}