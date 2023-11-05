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
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;

    private final OrderService orderService;

    private final BoardService boardService;
    private final UserRepository userRepository;

    @GetMapping("/mypage")
    public String userPageMapping(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return "redirect:/mypages?id="+principalDetails.getUser().getId();
    }


    // 유저 페이지 접속
    @GetMapping("/mypages")
    public String userPage(Long id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        // 로그인이 되어있는 유저의 id와 유저 페이지에 접속하는 id가 같아야 함
        if (principalDetails.getUser().getId() == id || Objects.equals(principalDetails.getUser().getRole(), "ROLE_ADMIN")) {
            User user= userInfoService.findUser(id);
            model.addAttribute("userInfo", user);
            model.addAttribute("coupon", "현재 사용 가능한 쿠폰은 " + (user.getStamp() / 10) + "개 입니다.");

            return "/MyPage";
        } else {
            model.addAttribute("message", "잘못된 접근입니다..");
            model.addAttribute("searchUrl", "/");
            return "message";
        }
    }

    // 회원 정보 수정
//    @GetMapping("/mypage/update")

    public String userModify(Long id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println(principalDetails.getUser().getRole());
        // 로그인이 되어있는 유저의 id와 수정페이지에 접속하는 id가 같아야 함
        if (Objects.equals(principalDetails.getUser().getRole(), "ROLE_ADMIN") || principalDetails.getUser().getId() == id) {
            model.addAttribute("userInfo", userInfoService.findUser(id));
            return "information";
        } else {
            model.addAttribute("message", "잘못된 접근입니다..");
            model.addAttribute("searchUrl", "/");
            return "message";
        }

    }

    // 수정 실행
    @PostMapping("/mypage/updates")
    public String userUpdate(Long id,@AuthenticationPrincipal PrincipalDetails principalDetails, User user, Model model) {

        if (principalDetails.getUser().getId() == id || Objects.equals(principalDetails.getUser().getRole(), "ROLE_ADMIN")) {
            userInfoService.userModify(user);
            model.addAttribute("message", "계정정보 수정이 완료되었습니다.");
            model.addAttribute("searchUrl", "/");
            return "message";
        } else {
            model.addAttribute("message", "잘못된 접근입니다..");
            model.addAttribute("searchUrl", "/");
            return "message";
        }
    }
    @GetMapping("/mypage/update/deletes")
    public String userDeleteMapping(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return "redirect:/mypage/deletes?id="+principalDetails.getUser().getId();
    }
    @GetMapping("/mypage/delete")
    public String userDelete(Long id, @AuthenticationPrincipal PrincipalDetails principalDetails , Model model){
        if (principalDetails.getUser().getId() == id) {
            userInfoService.delete(id);
            model.addAttribute("message", "이용해주셔서 감사합니다.");
            model.addAttribute("searchUrl", "/logout");
            return "message";
//            관리자가 삭제할 경우
        } else if ( Objects.equals(principalDetails.getUser().getRole(), "ROLE_ADMIN")) {
            userInfoService.delete(id);
            model.addAttribute("message", "삭제완료.");
            model.addAttribute("searchUrl", "/admin/users");
            return "message";

        } else {
            model.addAttribute("message", "잘못된 접근입니다..");
            model.addAttribute("searchUrl", "/");
            return "message";
        }
    }



//    @GetMapping("/mypage/qna")
//    public String myQnA(){
//        return "MyQnA";
//    }

//    @GetMapping("mypage/order")
//    public String myOrder(){
//        return"MyOrderr";
//    }


    //자신의 주문내역조회
    @GetMapping("/mypage/order")
    public String listOrdersForUser(Model model, Principal principal) {
        //유저의 id값을 받아옴
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

