package com.example.dbcafe.member.controller;

import com.example.dbcafe.member.Service.BoardService;
import com.example.dbcafe.member.Service.OrderService;
import com.example.dbcafe.member.Service.UserInfoService;
import com.example.dbcafe.member.config.auth.PrincipalDetails;
import com.example.dbcafe.member.dto.BoardDTO;
import com.example.dbcafe.member.entity.OrderEntity;
import com.example.dbcafe.member.entity.User;
import com.example.dbcafe.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;

    private final OrderService orderService;

    private final BoardService boardService;
    private final UserRepository userRepository;


    @GetMapping("/mypage")
    public String mypage(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        User user = principalDetails.getUser();
        model.addAttribute("userInfo",user);
        return "mypage";}

//    @GetMapping("/mypage/qna")
//    public String myQnA(){
//        return "MyQnA";
//    }

//    @GetMapping("mypage/order")
//    public String myOrder(){
//        return"MyOrderr";
//    }

    @GetMapping("/mypage/order/{userId}")
    public String listOrdersForUser(@PathVariable Long userId, Model model) {
        List<OrderEntity> userOrders = orderService.getOrdersForUser(userId); // 해당 유저의 주문 데이터를 가져오는 메서드를 구현해야 합니다.
        model.addAttribute("orders", userOrders);
        return"MyOrderr";
    }

    @GetMapping("/mypage/update")
    public String infoModify(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        User user = principalDetails.getUser();
        model.addAttribute("userInfo",user);
        return "information";
    }
    @PostMapping("/mypage/update")
    public String infoUpdate(@AuthenticationPrincipal PrincipalDetails principalDetails, Model model){
        User usertemp = userInfoService.getUserByUsername(principalDetails.getUser().getUsername());


        return "massage";
    }


    //QnA 글목록 페징처리포함
    @GetMapping("/mypage/qna")
    public String myQnA(@PageableDefault(page = 1, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                      Model model, Principal principal) {

        String loggedInUser = null; // 기본값으로 null 설정

        if (principal != null) {
            loggedInUser = principal.getName(); // 로그인된 사용자의 이름 설정
        }

        if (loggedInUser != null) {
            User user = userRepository.findByname(loggedInUser);
            if (user != null) {
                loggedInUser = user.getUsername(); // 사용자의 username으로 설정
            }
        }


//        String loggedInUser = "wnsgh";

        String search = loggedInUser;
        String searchCategory = "writer";

        Page<BoardDTO> boardList;

        if (search != null && !search.isEmpty()) {
            boardList = boardService.searchBoard(search, searchCategory, pageable);
        } else {
            boardList = boardService.paging(pageable);
        }

        int visiblePages = 3;
        int halfVisiblePages = visiblePages / 2;
        int currentPage = pageable.getPageNumber();
        int totalPages = boardList.getTotalPages();
        int startPage, endPage;

        if (totalPages <= visiblePages) {
            startPage = 1;
            endPage = totalPages;
        } else if (currentPage - halfVisiblePages <= 0) {
            startPage = 1;
            endPage = visiblePages;
        } else if (currentPage + halfVisiblePages >= totalPages) {
            startPage = totalPages - visiblePages + 1;
            endPage = totalPages;
        } else {
            startPage = currentPage - halfVisiblePages;
            endPage = currentPage + halfVisiblePages;
        }

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("search", search);
        model.addAttribute("searchCategory", searchCategory);

        return "MyQnA";
    }

}

