package com.example.dbcafe.member.repository;

import com.example.dbcafe.member.entity.BoardEntitiy;
import com.example.dbcafe.member.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
    @Modifying
    @Query(value = "update BrandEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id")Long id);
}
