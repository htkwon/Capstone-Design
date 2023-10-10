package com.hansung.hansungcommunity.entity;

import com.hansung.hansungcommunity.dto.free.FreeBoardRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "free_board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class FreeBoard extends Board {
    @Id
    private Long id;

    private FreeBoard(String title, String content, User user) {
        super(title, content);
        super.setUser(user);
    }

    // 생성 메소드
    public static FreeBoard createBoard(User user, FreeBoardRequestDto dto) {
        return new FreeBoard(
                dto.getTitle(),
                dto.getContent(),
                user
        );
    }

    public void setId(Long id) {
        this.id = id;
    }

    // 비즈니스 메소드
    public void patch(FreeBoardRequestDto dto) {
        updateTitleAndContent(dto.getTitle(), dto.getContent());
    }

}
