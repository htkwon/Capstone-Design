package com.hansung.hansungcommunity.dto.user;

import com.hansung.hansungcommunity.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserBookmarkDto {

    private Long id;
    private String name;
    private String nickname;
    //TODO: 현재 User entity Auditing 미적용
    //private LocalDateTime createdAt;
    //private LocalDateTime modifiedAt;

    //Testcode 및 생성의 편의를 위한 Factory method
    public static UserBookmarkDto of(Long id, String name, String nickname) {
        return new UserBookmarkDto(id, name, nickname);
    }

    public static UserBookmarkDto from(User entity) {
        return new UserBookmarkDto(
                entity.getId(),
                entity.getName(),
                entity.getNickname()
        );
    }

    public static UserBookmarkDto of(User user) {
        return new UserBookmarkDto(
                user.getId(),
                user.getName(),
                user.getNickname()
        );
    }
}
