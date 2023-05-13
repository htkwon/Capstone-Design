package com.hansung.hansungcommunity.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class UserUpdateDto {

    private String introduce;
    private List<String> skills;

}
