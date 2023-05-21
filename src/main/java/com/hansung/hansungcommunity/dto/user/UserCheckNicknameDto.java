package com.hansung.hansungcommunity.dto.user;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UserCheckNicknameDto {

    @Size(min = 2, max = 8)
    private String nickname;

}
