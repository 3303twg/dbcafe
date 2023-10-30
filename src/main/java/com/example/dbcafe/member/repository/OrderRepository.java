package com.example.dbcafe.member.repository;

import com.example.dbcafe.member.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
