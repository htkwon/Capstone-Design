package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.User;
import lombok.Data;

@Data
public class AdminUserDto {

    private Long id;
    private String stuId;
    private String nickname;

    public AdminUserDto(Long id, String studentId, String nickname) {
        this.id = id;
        this.stuId = studentId;
        this.nickname = nickname;
    }


    public static AdminUserDto of(User user) {
        return new AdminUserDto(
                user.getId(),
                user.getStudentId(),
                user.getNickname()
        );
    }

}
