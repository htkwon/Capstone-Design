package com.hansung.hansungcommunity.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class FreeBoardBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "free_board_id")
    private FreeBoard freeBoard;

    public FreeBoardBookmark(User user, FreeBoard freeBoard) {
        this.user = user;
        this.freeBoard = freeBoard;
    }

    public static FreeBoardBookmark of(User user, FreeBoard freeBoard) {
        return new FreeBoardBookmark(
                user,
                freeBoard
        );
    }

}
