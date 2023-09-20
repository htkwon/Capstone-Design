package com.hansung.hansungcommunity.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserCheckNicknameDto {

    @Size(min = 2, max = 8)
    private String nickname;

}
