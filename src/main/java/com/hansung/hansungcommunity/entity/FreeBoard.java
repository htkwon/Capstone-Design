package com.hansung.hansungcommunity.entity;

import com.hansung.hansungcommunity.dto.free.FreeBoardRequestDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Table(name = "free_board")
@Getter
@Setter
@Entity
public class FreeBoard extends Board {

    @Id
    private Long id;
    @NotNull
    private String title; // 제목
    @NotNull
    private String content; // 내용

    @OneToMany(fetch = FetchType.LAZY)
    private List<FileEntity> fileEntity = new ArrayList<>();

    @OneToMany(mappedBy = "freeBoard")
    private Set<FreeBoardBookmark> bookmarks = new HashSet<>();
    @OneToMany(mappedBy = "freeBoard")
    public List<FreeReply> replies = new ArrayList<>();


    // 생성 메소드
    public static FreeBoard createBoard(User user, FreeBoardRequestDto dto) {
        FreeBoard board = new FreeBoard();

        board.setUser(user); // 연관관계 설정

        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());

        return board;
    }

    // 연관관계 메소드
    public void setUser(User user) {
        super.setUser(user);
        user.getPostFreeBoards().add(this); // 필요한가?
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
