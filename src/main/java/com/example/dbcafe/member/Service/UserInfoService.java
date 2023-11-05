package com.example.dbcafe.member.Service;

import com.example.dbcafe.member.entity.CartEntity;
import com.example.dbcafe.member.entity.User;
import com.example.dbcafe.member.repository.CartRepositoty;
import com.example.dbcafe.member.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.dbcafe.member.repository.UserRepository;

import java.util.Optional;

@Service
public class UserInfoService {
    private final UserRepository userRepository;
    private final CartRepositoty cartRepositoty;

    @Autowired
    private CartService cartService;

    @Autowired
    public UserInfoService(UserRepository userRepository, CartRepositoty cartRepositoty) {
        this.userRepository = userRepository;
        this.cartRepositoty = cartRepositoty;
    }

    public User findUser(Long id) {
        return userRepository.findById(id).get();
    }


    public Long getUserIdByUsername(String username) {
        // 데이터베이스에서 사용자를 찾아 사용자 ID를 가져옴
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    // 회원 정보 수정
    public void userModify(User user) {
        User updateEntity = userRepository.findByUsername(user.getUsername());
        updateEntity.setUsername(user.getUsername());
        updateEntity.setEmail(user.getEmail());
        updateEntity.setGender(user.getGender());
        updateEntity.setPhone(user.getPhone());
        userRepository.save(updateEntity);

    }

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        }
        return null;

    }

    public void addStamp(Long userId, int valueToAdd) {
        User user = userRepository.findById(userId).orElse(null);
        CartEntity cart = cartRepositoty.findById(userId).orElse(null);
        if (user != null) {
            int total_price = cart.getTotalPrice();
            int value = total_price / 1000;
            int currentStamp = user.getStamp();
            user.setStamp(currentStamp + value);
            userRepository.save(user);
        }
    }

    public void usecoupon(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            if (user.getStamp() >= 10) {
                user.setStamp(user.getStamp() - 10); // 쿠폰사용량은 10개
                userRepository.save(user);
                cartService.addToCart(userId, 1L); //1번은 표시하지않는 메뉴로 설계할것임
            }
        }
    }

}