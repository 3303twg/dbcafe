package com.example.dbcafe.member.dto;


import com.example.dbcafe.member.entity.BoardEntitiy;
import com.example.dbcafe.member.entity.NoticeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {
    private Long id;
    private String noticeWriter; //작성자의 ID
    private String noticeTitle;
    private String noticeContents;
    private int noticeHits;
    private LocalDateTime noticeCreatedTime;
    private LocalDateTime noticeUpdatedTime;

    public NoticeDTO(Long id, String noticeWriter, String noticeTitle, int noticeHits, LocalDateTime noticeCreatedTime) {
        this.id = id;
        this.noticeWriter = noticeWriter;
        this.noticeTitle = noticeTitle;
        this.noticeHits = noticeHits;
        this.noticeCreatedTime = noticeCreatedTime;
    }

    public static NoticeDTO toNoticeDTO(NoticeEntity noticeEntity){
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setId(noticeEntity.getId());
        noticeDTO.setNoticeWriter(noticeEntity.getNoticeWriter());
//        boardDTO.setBoardPass(boardEntitiy.getBoardPass());
        noticeDTO.setNoticeTitle(noticeEntity.getNoticeTitle());
        noticeDTO.setNoticeContents(noticeEntity.getNoticeContents());
        noticeDTO.setNoticeHits(noticeEntity.getNoticeHits());
        noticeDTO.setNoticeCreatedTime(noticeEntity.getCretedTime());
        noticeDTO.setNoticeUpdatedTime(noticeEntity.getUpdateTime());
        return noticeDTO;
    }

}
