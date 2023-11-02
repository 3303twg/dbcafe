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


    // 유저 페이지 접속
    @GetMapping("/mypage/{userId}")
    public String userPage(@PathVariable("userId") Long id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        // 로그인이 되어있는 유저의 id와 유저 페이지에 접속하는 id가 같아야 함
        if (principalDetails.getUser().getId() == id) {

            model.addAttribute("userInfo", userInfoService.findUser(id));

            return "/MyPage";
        } else {
            return "redirect:/";
        }
    }

    // 회원 정보 수정
    @GetMapping("/mypage/update/{userId}")
    public String userModify(@PathVariable("userId") Long id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        // 로그인이 되어있는 유저의 id와 수정페이지에 접속하는 id가 같아야 함
        if (principalDetails.getUser().getId() == id) {
            model.addAttribute("userInfo", userInfoService.findUser(id));
            return "information";
        } else {
            return "redirect:/";
        }

    }

    // 수정 실행
    @PostMapping("/mypage/update/{userId}")
    public String userUpdate(@PathVariable("userId") Long id, User user, Model model) {

        userInfoService.userModify(user);
        model.addAttribute("message", "계정정보 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/");
        return "message";
    }



//    @GetMapping("/mypage/qna")
//    public String myQnA(){
//        return "MyQnA";
//    }

//    @GetMapping("mypage/order")
//    public String myOrder(){
//        return"MyOrderr";
//    }

    @GetMapping("/mypage/order")
    public String listOrdersForUser(Model model, Principal principal) {
        String username = (String) principal.getName();
        Long loggedInUser = userInfoService.getUserIdByUsername(username);

        List<OrderEntity> userOrders = orderService.getOrdersForUser(loggedInUser); // 해당유저의 주문데이터를 가져오는 메서드
        model.addAttribute("orders", userOrders);
        return"MyOrder";
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

        return "MyQ&AA";
    }

}

