package com.hansung.hansungcommunity.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "adopt")
@ToString
public class Adopt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adopt_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "qna_board_id")
    private Board qnaBoard;

    private Adopt(Board board, User user) {
        this.qnaBoard = board;
        this.user = user;
    }

    public static Adopt of(Board board, User user) {
        return new Adopt(
                board,
                user
        );
    }

}
