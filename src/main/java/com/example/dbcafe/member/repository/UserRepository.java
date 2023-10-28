package com.example.dbcafe.member.repository;

import com.example.dbcafe.member.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.servlet.http.HttpSession;
import java.util.Optional;

// <Entity, Entity-id>
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Override
    Optional<User> findById(Long id);
}