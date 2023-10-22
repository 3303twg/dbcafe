package com.example.dbcafe.member.entity;

import com.example.dbcafe.member.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntitiy extends BassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    //@Column(length = 20, nullable = false)
    //private String boardWriter;

    @Column
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    public static BoardEntitiy toSaveEntitiy(BoardDTO boardDTO){
        BoardEntitiy boardEntitiy = new BoardEntitiy();
        //boardEntitiy.setBoardWriter(boardDTO.getBoardWriter());
        boardEntitiy.setBoardPass(boardDTO.getBoardPass());
        boardEntitiy.setBoardTitle(boardDTO.getBoardTitle());
        boardEntitiy.setBoardContents(boardDTO.getBoardContents());
        boardEntitiy.setBoardHits(0);

        return  boardEntitiy;

    }


    public static BoardEntitiy toUpdateEntity(BoardDTO boardDTO) {
        BoardEntitiy boardEntitiy = new BoardEntitiy();
        boardEntitiy.setId(boardDTO.getId());//위랑 다르게 아이디가있어야 업데이트함
        //boardEntitiy.setBoardWriter(boardDTO.getBoardWriter());
        boardEntitiy.setBoardPass(boardDTO.getBoardPass());
        boardEntitiy.setBoardTitle(boardDTO.getBoardTitle());
        boardEntitiy.setBoardContents(boardDTO.getBoardContents());
        boardEntitiy.setBoardHits(boardDTO.getBoardHits());
        return  boardEntitiy;
    }
}
