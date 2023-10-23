package com.example.dbcafe.member.controller;


import com.example.dbcafe.member.Service.BoardService;
import com.example.dbcafe.member.dto.BoardDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor


public class BoardController {
    private final BoardService boardService;

    @GetMapping("/text")
    public String text(){
        return "Q&A_write";
    }

    @PostMapping("/text")
    public String text(@ModelAttribute BoardDTO boardDTO){
        boardService.save(boardDTO);

        return "index";
    }

    @GetMapping("/list")
    public String findAll(Model model){
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";

    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable Long id, Model model, @PageableDefault(page=1) Pageable pageable){

        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        model.addAttribute("Page", pageable.getPageNumber());
        return "detail";
    }

    @GetMapping("/board/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "update";
    }

    @PostMapping("/board/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model){
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        return "detail";
    }

    @GetMapping("/board/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/QnA/";
    }

    // /board/paging?page=1
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

    @GetMapping("/board/QnA")
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

        return "Q&A";
    }

}
