package com.hansung.hansungcommunity.entity;

import com.hansung.hansungcommunity.dto.free.FreeBoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "free_board")
@Getter
@Setter
@NoArgsConstructor
@Entity
public class FreeBoard extends Board {

    @Id
    private Long id;

    private FreeBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 생성 메소드
    public static FreeBoard createBoard(User user, FreeBoardRequestDto dto) {
        FreeBoard board = new FreeBoard(
                dto.getTitle(),
                dto.getContent()
        );

        board.setUser(user); // 연관관계 설정

        return board;
    }

    // 연관관계 메소드
    public void setUser(User user) {
        super.setUser(user);
    }

    // 비즈니스 메소드
    public void patch(FreeBoardRequestDto dto) {
        if (dto.getTitle() != null)
            this.title = dto.getTitle();

        if (dto.getContent() != null)
            this.content = dto.getContent();

        modified();
    }

    public void increaseHits() {
        increaseViews();
    }

}
