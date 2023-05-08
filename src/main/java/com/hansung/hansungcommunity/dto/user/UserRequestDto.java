package com.hansung.hansungcommunity.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class UserRequestDto {

    private String studentId;
    private String name; // 이름
    private String nickname; // 닉네임
    private String introduce; // 소개글
    private String track1;
    private String track2;
    private List<String> skills; // 관심 기술
    private String picture;

}
