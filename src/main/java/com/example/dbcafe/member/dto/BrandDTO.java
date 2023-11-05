package com.example.dbcafe.member.dto;


import com.example.dbcafe.member.entity.BoardEntitiy;
import com.example.dbcafe.member.entity.BrandEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BrandDTO {
    private Long id;
    private String boardWriter;
    //    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;

    public BrandDTO(Long id, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
    }

    public static BrandDTO toBoardDTO(BrandEntity brandEntity){
        BrandDTO brandDTO = new BrandDTO();
        brandDTO.setId(brandEntity.getId());
        brandDTO.setBoardWriter(brandEntity.getBoardWriter());
//        boardDTO.setBoardPass(boardEntitiy.getBoardPass());
        brandDTO.setBoardTitle(brandEntity.getBoardTitle());
        brandDTO.setBoardContents(brandEntity.getBoardContents());
        brandDTO.setBoardHits(brandEntity.getBoardHits());
        brandDTO.setBoardCreatedTime(brandEntity.getCretedTime());
        brandDTO.setBoardUpdatedTime(brandEntity.getUpdateTime());
        return brandDTO;
    }
}
