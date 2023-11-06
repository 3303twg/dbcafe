package com.example.dbcafe.member.controller;


import com.example.dbcafe.member.Service.BoardService;
import com.example.dbcafe.member.Service.NoticeService;
import com.example.dbcafe.member.config.auth.PrincipalDetails;
import com.example.dbcafe.member.dto.BoardDTO;
import com.example.dbcafe.member.dto.NoticeDTO;
import com.example.dbcafe.member.entity.NoticeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor

//보드를 고정으로 넣음
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final NoticeService noticeService;



    //글 작성
    @GetMapping("/QnA/write") //작성창을 띄우기위한 매핑
    public String text(@AuthenticationPrincipal PrincipalDetails principalDetails){
        //비로그인일경우 로그인페이지로 보냄
        if(principalDetails == null){
            return "redirect:/signin";
        }
        else {
            return "Q&A_write";
        }
    }

    @PostMapping("/QnA/write") //작성한 내용을 저장하기위한 매핑
    public String text(@ModelAttribute BoardDTO boardDTO , @AuthenticationPrincipal PrincipalDetails principalDetails){
        //작성자를 따로 작성하지않아도 자동으로 데이터가 들어가게함
        boardDTO.setBoardWriter(principalDetails.getUsername());
        boardService.save(boardDTO);

       return "redirect:/board/QnA/";
    }

    //공지사항 글작성
    @GetMapping("/notice/write")
    public String noticewrite(){
        return "notice_write";
    }
    //공지사항 글작성
    @PostMapping("/notice/write")
    public String noticewrite(@ModelAttribute NoticeDTO noticeDTO ,@AuthenticationPrincipal PrincipalDetails principalDetails){
        //작성자를 따로 작성하지않아도 자동으로 데이터가 들어가게함
        noticeDTO.setNoticeWriter(principalDetails.getUsername());
        noticeService.save(noticeDTO);

        return "redirect:/board/notice/";
    }



    //안씀 테스트용 리스트
    @GetMapping("/list")
    public String findAll(Model model){
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }


    //QNA 글상세조회 공지가 하나라도 있어야 작동함 ㅇㅇ 수정하려면 qna.html에서 조건문으로 공지글 찾아오는거 빼야함
    @GetMapping("/QnA/{id}")
    public String findById(@PathVariable Long id, Model model, @PageableDefault(page=1) Pageable pageable){
        //조회수를 증가시키는 서비스
        boardService.updateHits(id);
        //DTO를 받아온후 html에사용하기위해 모델에 띄움
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        model.addAttribute("Page", pageable.getPageNumber());
        return "Q&A_check";
    }

    //공지사항 상세조회
    @GetMapping("/notice/{id}")
    public String noticefindById(@PathVariable Long id, Model model, @PageableDefault(page=1) Pageable pageable){
        //조회수를 증가시키는 서비스
        noticeService.updateHits(id);
        //DTO를 받아온후 html에사용하기위해 모델에 띄움
        NoticeDTO noticeDTO = noticeService.findById(id);
        model.addAttribute("board", noticeDTO);             //모델은 수정할필요 없겠지??
        model.addAttribute("Page", pageable.getPageNumber()); //없겠지???
        return "notice_check";        //일단은 없으니까 공지 = QnA 똑같은거로 알아서 같이쓰는중
    }

    //QnA 글수정1
    @GetMapping("/QnA/update/{id}")
    public String updateForm(@PathVariable Long id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
        //비로그인시 로그인창으로 보냄
        if(principalDetails == null){
            return "redirect:/signin";
        }
        else {//로그인상태일경우
            //DTO를 가져와서 기존작성내용을 띄워줌
            BoardDTO boardDTO = boardService.findById(id);
            boardDTO.setBoardWriter(principalDetails.getUsername());
            model.addAttribute("boardUpdate", boardDTO);
            return "Q&A_write_modify";
        }
    }

    //QnA 글수정2
    @PostMapping("/QnA/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model){
        //작성된 내용을 업데이트함
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        return "redirect:/board/QnA";
    }

    //공지사항 글수정1
    @GetMapping("/notice/update/{id}")
    public String noticeupdateForm(@PathVariable Long id, Model model){
        //DTO를 가져와서 기존작성내용을 띄워줌
        NoticeDTO noticeDTO = noticeService.findById(id);
        model.addAttribute("boardUpdate", noticeDTO);
        return "notice_write_modify"; //마찬가지
    }

    //공지사항 글수정2
    @PostMapping("/notice/update")
    public String noticeupdate(@ModelAttribute NoticeDTO noticeDTO, Model model){
        //작성된 내용을 업데이트함
        NoticeDTO notice = noticeService.update(noticeDTO);
        model.addAttribute("board", notice);
        return "redirect:/board/notice";
    }


    //QnA 글삭제
    @GetMapping("/QnA/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/board/QnA/";
    }

    //공지사항 글삭제
    @GetMapping("/notice/delete/{id}")
    public String noticedelete(@PathVariable Long id){
        noticeService.delete(id);
        return "redirect:/board/notice/";
    }

    // /board/paging?page=1 페이징 테스트용 매핑
    @GetMapping("/board/paging")
    public String paging(@PageableDefault(page = 1)Pageable pageable, Model model){
        pageable.getPageNumber();
        Page<BoardDTO> boardList = boardService.paging(pageable);

        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1 ) * blockLimit +1;
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit -1 : boardList.getTotalPages();

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "paging";
    }


    //QnA 글목록 페징처리포함
    @GetMapping("/QnA")
    public String QnA(@PageableDefault(page = 1, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                      Model model,
                      @RequestParam(name = "search", required = false) String search,
                      @RequestParam(value = "search_category", required = false) String searchCategory) {

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

        NoticeEntity lastNotice = noticeService.getLatestNotice();
        model.addAttribute("lastNotice", lastNotice);

        return "Q&A";
    }

    //공지사항 글목록 //html안에 타임리프로 DTO 꺼내쓰는거 수정해줘야함
    @GetMapping("/notice")
    public String notice(@PageableDefault(page = 1, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                      Model model,
                      @RequestParam(name = "search", required = false) String search,
                      @RequestParam(value = "search_category", required = false) String searchCategory) {

        Page<NoticeDTO> noticeList;

        if (search != null && !search.isEmpty()) {
            noticeList = noticeService.searchBoard(search, searchCategory, pageable);
        } else {
            noticeList = noticeService.paging(pageable);
        }

        int visiblePages = 3;
        int halfVisiblePages = visiblePages / 2;
        int currentPage = pageable.getPageNumber();
        int totalPages = noticeList.getTotalPages();
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

        model.addAttribute("boardList", noticeList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("search", search);
        model.addAttribute("searchCategory", searchCategory);

        return "NOTICE";
    }

}
