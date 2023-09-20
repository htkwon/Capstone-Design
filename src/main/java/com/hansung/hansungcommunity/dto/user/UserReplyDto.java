package com.hansung.hansungcommunity.dto.user;

import com.hansung.hansungcommunity.entity.User;
import lombok.Getter;

@Getter
public class UserReplyDto {

    private Long id;
    private String nickname;
    private String profileImg;

    private UserReplyDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickname();
        this.profileImg = user.getProfileImg();
    }

    public static UserReplyDto from(User user) {
        return new UserReplyDto(user);
    }

}
