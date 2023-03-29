package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.UserRequestDto;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // 생성자 주입 (final 키워드)
@Transactional(readOnly = true) // 읽기 전용
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원가입
     */
    @Transactional // 필요 시 쓰기 전용
    public Long join(UserRequestDto dto) {

        User user = userRepository.save(User.of(dto));

        return user.getId();
    }

    public boolean checkUser(String stuId) {

        return userRepository.existsUserByStudentId(stuId);
    }

}
