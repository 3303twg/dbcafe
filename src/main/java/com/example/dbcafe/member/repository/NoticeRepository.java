package com.example.dbcafe.member.repository;

import com.example.dbcafe.member.entity.BoardEntitiy;
import com.example.dbcafe.member.entity.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    @Modifying
    @Query(value = "update NoticeEntity b set b.noticeHits=b.noticeHits+1 where b.id=:id")
    void updateHits(@Param("id")Long id);

    Page<NoticeEntity> findByNoticeTitleContaining(String search, Pageable pageable); //제목포함검색

    Page<NoticeEntity> findByNoticeContentsContaining(String search, Pageable pageable); //내용포함검색


}
