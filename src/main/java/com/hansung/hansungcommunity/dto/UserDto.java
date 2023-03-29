package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private int point;
    private String nickname;
    //TODO: 현재 User entity Auditing 미적용
    //private LocalDateTime createdAt;
    //private LocalDateTime modifiedAt;

    //Testcode 및 생성의 편의를 위한 Factory method
    public static UserDto of(Long id, String name, int point, String nickname){
        return new UserDto(id,name,point,nickname);
    }

    public static UserDto from(User entity){
        return new UserDto(
                entity.getId(),
                entity.getName(),
                entity.getPoint(),
                entity.getNickname()
        );
    }

}
