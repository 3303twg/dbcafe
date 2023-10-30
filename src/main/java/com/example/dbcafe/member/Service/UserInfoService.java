package com.example.dbcafe.member.Service;

import com.example.dbcafe.member.entity.User;
import com.example.dbcafe.member.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.dbcafe.member.repository.UserRepository;

@Service
public class UserInfoService {
    private final UserRepository userRepository;

    @Autowired
    public UserInfoService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long getUserIdByUsername(String username) {
        // 데이터베이스에서 사용자를 찾아 사용자 ID를 가져옴
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getId();
        }
        return null;
    }
    public User getUserByUsername(String username){
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        }
        return null;

    }
}