package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.User;
import lombok.Getter;

@Getter
public class AdminUserDto {

    private final Long id;
    private final String stuId;
    private final String nickname;

    private AdminUserDto(Long id, String studentId, String nickname) {
        this.id = id;
        this.stuId = studentId;
        this.nickname = nickname;
    }

    public static AdminUserDto from(User user) {
        return new AdminUserDto(
                user.getId(),
                user.getStudentId(),
                user.getNickname()
        );
    }

}
