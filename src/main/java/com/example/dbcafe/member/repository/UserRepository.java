package com.example.dbcafe.member.repository;

import com.example.dbcafe.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.servlet.http.HttpSession;

// <Entity, Entity-id>
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}