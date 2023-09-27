package com.hansung.hansungcommunity.entity;

import com.hansung.hansungcommunity.dto.qna.QnaReplyDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "qna_reply")
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QnaReply extends Reply {

    private boolean adopt;

    private QnaReply(User user, QnaBoard board, String article) {
        super(user, board, article);
    }

    public static QnaReply of(User user, QnaBoard board, QnaReplyDto replyDto) {
        return new QnaReply(
                user,
                board,
                replyDto.getArticle()
        );
    }

    public void updateParent(QnaReply parent) {
        super.updateParent(parent);
    }

    public void adopt(boolean adopt) {
        this.adopt = adopt;
    }

    public void update(String article) {
        super.update(article);
    }

}
