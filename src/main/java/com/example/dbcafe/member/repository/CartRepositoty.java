package com.example.dbcafe.member.repository;

import com.example.dbcafe.member.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepositoty extends JpaRepository<CartEntity, Long> {
}
