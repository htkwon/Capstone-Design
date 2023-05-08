package com.hansung.hansungcommunity.dto.user;

import com.hansung.hansungcommunity.entity.User;
import lombok.Data;

/**
 * 단순 유저 정보를 담는 DTO
 */
@Data
public class SimpleUserInfoDto {

    private Long id;
    private String studentId;
    private String name;
    private int point;
    private String nickname;
    private String profileImg;
    private String career;
    private String introduce;
    private String track1;
    private String track2;

    public SimpleUserInfoDto(User user) {
        this.id = user.getId();
        this.studentId = user.getStudentId();
        this.name = user.getName();
        this.point = user.getPoint();
        this.nickname = user.getNickname();
        this.profileImg = user.getProfileImg();
        this.career = user.getCareer();
        this.introduce = user.getIntroduce();
        this.track1 = user.getTrack1();
        this.track2 = user.getTrack2();
    }

}
