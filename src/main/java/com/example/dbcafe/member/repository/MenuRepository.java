package com.example.dbcafe.member.repository;

import com.example.dbcafe.member.entity.MenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<MenuEntity,Long>{

}
