package com.hansung.hansungcommunity.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "adopt")
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
    private QnaBoard qnaBoard;

    public Adopt(QnaBoard board, User user) {
        this.qnaBoard = board;
        this.user = user;
    }

    public static Adopt of(QnaBoard board, User user) {
        return new Adopt(
                board,
                user
        );
    }

}
