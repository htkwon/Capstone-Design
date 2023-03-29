package com.hansung.hansungcommunity.dto;

import lombok.Data;

@Data
public class UserRequestDto {



    private String studentId;
    private String name; // 이름

    private String nickname; // 닉네임
    private String introduce; // 소개글
    private String track1;
    private String track2;


}
