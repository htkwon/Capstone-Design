package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.user.UserCheckNicknameDto;
import com.hansung.hansungcommunity.dto.user.UserInfoDto;
import com.hansung.hansungcommunity.dto.user.UserRankDto;
import com.hansung.hansungcommunity.dto.user.UserRequestDto;
import com.hansung.hansungcommunity.entity.Skill;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.repository.AdoptRepository;
import com.hansung.hansungcommunity.repository.SkillRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // 생성자 주입 (final 키워드)
@Transactional(readOnly = true) // 읽기 전용
public class UserService {

    private final UserRepository userRepository;
    private final AdoptRepository adoptRepository;
    private final SkillRepository skillRepository;
    private final MyPageService myPageService;


    /**
     * 회원가입
     */
    @Transactional // 필요 시 쓰기 전용
    public Long join(UserRequestDto dto) {
        validateDuplicateUser(dto);
        Set<Skill> skills = dto.getSkills().stream().map(s -> skillRepository.findByName(s)
                        .orElseThrow(() -> new IllegalArgumentException("관심 기술 등록 실패, 해당하는 기술이 없습니다.")))
                .collect(Collectors.toSet());

        User user = userRepository.save(User.from(dto, skills));

        return user.getId();
    }

    private void validateDuplicateUser(UserRequestDto dto) {
        if (checkUser(dto.getStudentId())) {
            throw new IllegalStateException("이미 존재하는 학생입니다.");
        }
    }

    public boolean checkUser(String stuId) {
        return userRepository.existsUserByStudentId(stuId);
    }

    public Optional<User> getByStudentId(String studentId) {
        return userRepository.findByStudentId(studentId);
    }

    public UserInfoDto getUserInfo(Long stuId) {
        User user = userRepository.findById(stuId).orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));
        UserInfoDto userInfoDto = UserInfoDto.from(user);
        userInfoDto.setReply(myPageService.getReplyList(stuId).size());
        userInfoDto.setBoard(myPageService.getBoardList(stuId).size());
        userInfoDto.setBookmark(myPageService.getBookmarkList(stuId).size());
        return userInfoDto;
    }

    public List<UserRankDto> getUserRank() {
        List<UserRankDto> list = adoptRepository.findTop5UsersByAdoptCount()
                .stream()
                .map(UserRankDto::of)
                .limit(5)
                .collect(Collectors.toList());
        return list;
    }

    public Boolean checkUserNickname(UserCheckNicknameDto dto) {
        User user = userRepository.findByNickname(dto.getNickname());
        return user != null;
    }

}
