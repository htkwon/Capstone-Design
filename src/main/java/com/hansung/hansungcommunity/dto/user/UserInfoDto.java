package com.hansung.hansungcommunity.dto.user;

import com.hansung.hansungcommunity.entity.User;
import lombok.Data;

@Data
public class UserInfoDto {

    private Long id;
    private String studentId;
    private String nickname;
    private String name;

    private String track1;

    private String track2;

    public UserInfoDto(Long id, String studentId, String name, String nickname, String track1, String track2) {
        this.id = id;
        this.studentId = studentId;
        this.name = name;
        this.nickname = nickname;
        this.track1 = track1;
        this.track2 = track2;
    }


    public static UserInfoDto from(User user) {
        return new UserInfoDto(
                user.getId(),
                user.getStudentId(),
                user.getName(),
                user.getNickname(),
                user.getTrack1(),
                user.getTrack2()

        );
    }
}
