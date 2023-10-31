package com.example.dbcafe.member.repository;

import com.example.dbcafe.member.entity.BoardEntitiy;
import com.example.dbcafe.member.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntitiy, Long> {
    @Modifying
    @Query(value = "update BoardEntitiy b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id")Long id);


    Page<BoardEntitiy> findByBoardTitleContaining(String search, Pageable pageable);

    Page<BoardEntitiy> findByBoardContentsContaining(String search, Pageable pageable);

    Page<BoardEntitiy> findByBoardWriter(String writer, Pageable pageable);

    Page<BoardEntitiy> findByBoardTitleContainingIgnoreCase(String search, Pageable pageable);



}



