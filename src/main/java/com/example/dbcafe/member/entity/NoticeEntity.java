package com.example.dbcafe.member.entity;


import com.example.dbcafe.member.dto.BoardDTO;
import com.example.dbcafe.member.dto.NoticeDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "notice_table")
public class NoticeEntity extends BassNoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column
    private String noticeTitle;

    @Column(length = 500)
    private String noticeContents;

    @Column
    private int noticeHits;


    public static NoticeEntity toSaveEntitiy(NoticeDTO noticeDTO){
        NoticeEntity noticeEntity = new NoticeEntity();
        //boardEntitiy.setBoardWriter(boardDTO.getBoardWriter());
//        boardEntitiy.setBoardPass(boardDTO.getBoardPass());
        noticeEntity.setNoticeTitle(noticeDTO.getNoticeTitle());
        noticeEntity.setNoticeContents(noticeDTO.getNoticeContents());
        noticeEntity.setNoticeHits(0);

        return noticeEntity;

    }


    public static NoticeEntity toUpdateEntity(NoticeDTO noticeDTO) {
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setId(noticeDTO.getId());//위랑 다르게 아이디가있어야 업데이트함
        //boardEntitiy.setBoardWriter(boardDTO.getBoardWriter());
//        boardEntitiy.setBoardPass(boardDTO.getBoardPass());
        noticeEntity.setNoticeTitle(noticeDTO.getNoticeTitle());
        noticeEntity.setNoticeContents(noticeDTO.getNoticeContents());
        noticeEntity.setNoticeHits(noticeDTO.getNoticeHits());
        return noticeEntity;
    }

}
