package com.hansung.hansungcommunity.entity;

import com.hansung.hansungcommunity.dto.FreeBoardDto;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Table(name = "free_board")
@Getter
@Setter
@Entity
public class FreeBoard extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "free_board_id")
    private Long id;
    @NotNull
    private String title; // 제목
    @NotNull
    private String content; // 내용
    @ColumnDefault(value = "0")
    private int hits; // 조회수
    @ColumnDefault(value = "0")
    private int bookmarks; // 북마크 횟수
    @ColumnDefault(value = "0")
    private int reports; // 신고 횟수

    @ManyToOne(fetch = FetchType.LAZY) // JPA 활용 시, XToOne 인 경우 fetch 타입을 LAZY 로 설정 !!!
    @JoinColumn(name = "stu_id")
    private User user;

    // private Image image; // 게시글 내부 이미지, 추후 개발

    // 생성 메소드
    public static FreeBoard createBoard(User user, FreeBoardDto dto) {
        FreeBoard board = new FreeBoard();

        board.setUser(user); // 연관관계 설정

        board.setTitle(dto.getTitle());
        board.setContent(dto.getContent());

        return board;
    }

    // 연관관계 메소드
    public void setUser(User user) {
        this.user = user;
        user.getPostFreeBoards().add(this); // 필요한가?
    }

    // 비즈니스 메소드
    public void patch(FreeBoardDto dto) {
        if (dto.getTitle() != null)
            this.title = dto.getTitle();

        if (dto.getContent() != null)
            this.content = dto.getContent();
    }

    // 조회수 증가 메소드
    public void increaseHits() {
        this.hits++;
    }
}
