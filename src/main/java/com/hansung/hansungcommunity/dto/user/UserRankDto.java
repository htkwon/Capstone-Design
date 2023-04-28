package com.hansung.hansungcommunity.dto.user;

import com.hansung.hansungcommunity.entity.Adopt;
import com.hansung.hansungcommunity.entity.User;
import lombok.Data;

@Data
public class UserRankDto {
    
    private int adoptSize;
    private String nickname;
    private String studentId;

    public UserRankDto(int adoptSize, String nickname, String studentId) {
        this.adoptSize = adoptSize;
        this.nickname = nickname;
        this.studentId = studentId;
    }

    //TODO : 프로필 이미지

    public static UserRankDto of(Adopt adopt){
        return new UserRankDto(
                adopt.getUser().getQnaAdopt().size(),
                adopt.getUser().getNickname(),
                adopt.getUser().getStudentId()
        );
    }

}
