package com.hansung.hansungcommunity.dto.user;

import com.hansung.hansungcommunity.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserReplyDto {

    private Long id;
    private String nickname;

    public UserReplyDto(User user){
        this.id = user.getId();
        this.nickname = user.getNickname();
    }

}
