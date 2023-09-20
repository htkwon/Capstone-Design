package com.hansung.hansungcommunity.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUpdateDto {

    @Size(min = 2, max = 8)
    private String nickname;
    @Size(max = 100)
    private String introduce;
    @Size(max = 7)
    private List<String> skills;

}
