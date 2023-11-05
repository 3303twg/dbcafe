package com.example.dbcafe.member.Service;

import com.example.dbcafe.member.dto.SignupDto;
import com.example.dbcafe.member.entity.CartEntity;
import com.example.dbcafe.member.entity.User;
import com.example.dbcafe.member.repository.CartRepositoty;
import com.example.dbcafe.member.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.dbcafe.member.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
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

    public List<SignupDto> findUserAll(){
        List<User>userEntityList=userRepository.findAll();
        List<SignupDto>userDTOList=new ArrayList<>();
//        각 리스트에 저장되어있는 entity를 dto로 변환하는 과정
        for(User userEntity:userEntityList)
        {
            userDTOList.add(userEntity.toDto());
        }

        return userDTOList;
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

    //스탬프를 추가하는 메서드
    public void addStamp(Long userId) {
        //유저 id를통해 유저의 엔티티를 가져옴
        User user = userRepository.findById(userId).orElse(null);
        //유저 id를통해 유저의 카트엔티티를 가져옴
        CartEntity cart = cartRepositoty.findById(userId).orElse(null);
        if (user != null) { // 유저엔티티가 존재할경우에만
            //가격 1천원당 스탬프를 한개줌
            int total_price = cart.getTotalPrice();
            int value = total_price / 1000;
            int currentStamp = user.getStamp();
            user.setStamp(currentStamp + value);
            userRepository.save(user);
        }
    }

    //쿠폰을 사용하는 메서드
    public void usecoupon(Long userId) {
        //유저의 엔티티를 가져옴
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {//유저의 엔티티가 있을경우에만
            if (user.getStamp() >= 10) { //쿠폰이 10개이상 있을경우에만
                user.setStamp(user.getStamp() - 10); // 쿠폰사용량은 10개
                userRepository.save(user);  //변경정보 저장
                cartService.addToCart(userId, 1L); //1번은 표시되지않는 메뉴
            }
        }
    }

    //유저정보 삭제
    public void delete(Long id){
        userRepository.deleteById(id);
    }

    //유저권한 상승
    public void roleUP(Long id){
        User user=userRepository.findById(id).get();
        user.setRole("ROLE_ADMIN");
        userRepository.save(user);
    }
    //유저권한 하락
    public void roleDOWN(Long id){
        User user=userRepository.findById(id).get();
        user.setRole("ROLE_USER");
        userRepository.save(user);
    }

}