package com.hansung.hansungcommunity.dto.recruit;

import com.hansung.hansungcommunity.entity.Skill;
import com.hansung.hansungcommunity.entity.User;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 신청자 정보를 담는 DTO
 */
@Data
public class ApplicantDto {

    private Long id;
    private String nickname;
    private String studentId;
    private String profileImg;
    private String track1;
    private Set<String> skills;
    private boolean isMeetRequired;
    private boolean isMeetOptional;

    public ApplicantDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.studentId = user.getStudentId();
        this.profileImg = user.getProfileImg();
        this.track1 = user.getTrack1();
        this.skills = user.getSkills().stream().map(Skill::getName).collect(Collectors.toSet());
    }

}
