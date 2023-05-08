package com.hansung.hansungcommunity.dto.user;

import com.hansung.hansungcommunity.entity.User;
import lombok.Data;

@Data
public class UserRankDto {

    private int adoptSize;
    private String nickname;
    private String studentId;
    private String profileImg;

    public UserRankDto(int adoptSize, String nickname, String studentId, String profileImg) {
        this.adoptSize = adoptSize;
        this.nickname = nickname;
        this.studentId = studentId;
        this.profileImg = profileImg;
    }

    //TODO : 프로필 이미지

    public static UserRankDto of(Object[] obj) {
        return new UserRankDto(
                ((Long) obj[1]).intValue(),
                ((User) obj[0]).getNickname(),
                ((User) obj[0]).getStudentId(),
                ((User) obj[0]).getProfileImg()
        );
    }

}
