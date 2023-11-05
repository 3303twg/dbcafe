package com.example.dbcafe.member.controller;

import com.example.dbcafe.member.Service.BoardService;
import com.example.dbcafe.member.Service.BrandService;
import com.example.dbcafe.member.config.auth.PrincipalDetails;
import com.example.dbcafe.member.dto.BoardDTO;
import com.example.dbcafe.member.dto.BrandDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequiredArgsConstructor
public class PartneshipController {
    private final BrandService brandService;


    @PostMapping("/partnership/write")
    public String brandtext(@ModelAttribute BrandDTO brandDTO , @AuthenticationPrincipal PrincipalDetails principalDetails){
        brandDTO.setBoardWriter(principalDetails.getUsername());
        brandService.save(brandDTO);

        return "redirect:/partnership";
    }
}
