package com.hansung.hansungcommunity.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class QnaBoardBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "qna_board_id")
    private QnaBoard qnaBoard;

    public QnaBoardBookmark(User user, QnaBoard qnaBoard) {
        this.user = user;
        this.qnaBoard = qnaBoard;
    }

    public static QnaBoardBookmark of(User user, QnaBoard qnaBoard) {
        return new QnaBoardBookmark(
                user,
                qnaBoard
        );
    }

}
