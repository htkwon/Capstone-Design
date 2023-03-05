package com.hansung.hansungcommunity.entity;

import com.hansung.hansungcommunity.dto.QnaReplyDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "qna_reply")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaReply extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_reply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_board_id")
    private QnaBoard board;

    @Column
    private String article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private QnaReply parent;

    @OneToMany(mappedBy = "parent")
    private List<QnaReply> children = new ArrayList<>();

    private QnaReply(User user, QnaBoard board, String article) {
        this.user = user;
        this.board = board;
        this.article = article;
    }

    public static QnaReply of(User user, QnaBoard board, QnaReplyDto replyDto) {
        return new QnaReply(
                user,
                board,
                replyDto.getArticle()
        );
    }

    public void updateParent(QnaReply parent) {
        this.parent = parent;
    }
}
