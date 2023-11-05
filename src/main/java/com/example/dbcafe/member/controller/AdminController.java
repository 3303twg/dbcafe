package com.example.dbcafe.member.controller;

import com.example.dbcafe.member.Service.MenuService;
import com.example.dbcafe.member.Service.UserInfoService;
import com.example.dbcafe.member.dto.MenuDTO;
import com.example.dbcafe.member.dto.SignupDto;
import com.example.dbcafe.member.entity.OrderEntity;
import com.example.dbcafe.member.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserInfoService userInfoService;
    private final MenuService menuService;

    @GetMapping("")
    public String adminMain(){
        return "adminMain";
    }
    @GetMapping("/users")
    public String userList(Model model){
        List<SignupDto> userDTOList = userInfoService.findUserAll();
        model.addAttribute("userList", userDTOList);
        return "userList";
    }
    @GetMapping("/users/role/up")
    public String userRoleUP(Long id){
        userInfoService.roleUP(id);
        return "redirect:/admin/users";
    }
    @GetMapping("/users/role/down")
    public String userRoleDOWN(Long id){
        userInfoService.roleDOWN(id);
        return "redirect:/admin/users";
    }
    @GetMapping("/menu")
    public String menuManage(Model model){
        List<MenuDTO> menuDTOList = menuService.menuFindAll();
        model.addAttribute("menuList", menuDTOList);
        return "menu";
    }
    @GetMapping("/menu/register")
    public String menuRegister(){return "menuRegister";}

    @PostMapping("/menu/register")
    public String menuSave(@ModelAttribute MenuDTO menuDTO , MultipartFile file , HttpServletRequest request, Model model) throws Exception {
        menuService.register(menuDTO,file,request);
        model.addAttribute("message", "등록되었습니다.");
        model.addAttribute("searchUrl", "/admin/menu");
        return "message";
    }
    @GetMapping("/menu/delete")
    public String menuDelete(Long id,HttpServletRequest request,Model model){

        menuService.delete(id,request);
        model.addAttribute("message", "삭제되었습니다.");
        model.addAttribute("searchUrl", "/admin/menu");
        return "message";

    }

    @Autowired
    private OrderRepository orderRepository;
    private OrderEntity orderEntity;
    @GetMapping("/order")
    public String listOrdersForUser(Model model, Principal principal) {

        List<OrderEntity> userOrders = orderRepository.findAll();
        model.addAttribute("orders", userOrders);

        int totaltotalprice=0;
        for (OrderEntity order : userOrders) {
            totaltotalprice = totaltotalprice+order.getTotalPrice();
        }

        model.addAttribute("totaltotalprice", totaltotalprice);

        return"orders";
    }
}
