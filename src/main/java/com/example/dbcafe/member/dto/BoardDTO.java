package com.example.dbcafe.member.dto;

import com.example.dbcafe.member.entity.BoardEntitiy;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;

    public BoardDTO(Long id, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
    }

    public static BoardDTO toBoardDTO(BoardEntitiy boardEntitiy){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntitiy.getId());
        boardDTO.setBoardWriter(boardEntitiy.getBoardWriter());
        boardDTO.setBoardPass(boardEntitiy.getBoardPass());
        boardDTO.setBoardTitle(boardEntitiy.getBoardTitle());
        boardDTO.setBoardContents(boardEntitiy.getBoardContents());
        boardDTO.setBoardHits(boardEntitiy.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntitiy.getCretedTime());
        boardDTO.setBoardUpdatedTime(boardEntitiy.getUpdateTime());
        return boardDTO;
    }
}


