package com.hansung.hansungcommunity.entity;

import com.hansung.hansungcommunity.dto.notice.NoticeReplyDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NoticeReply extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_reply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_board_id")
    private NoticeBoard noticeBoard;

    @Column
    private String article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private NoticeReply parent;

    @OneToMany(mappedBy = "parent")
    private List<NoticeReply> children = new ArrayList<>();

    private NoticeReply(User user, NoticeBoard noticeBoard, String article) {
        this.user = user;
        this.noticeBoard = noticeBoard;
        this.article = article;
    }

    public static NoticeReply of(User user, NoticeBoard board, NoticeReplyDto dto) {
        return new NoticeReply(
                user,
                board,
                dto.getArticle()
        );
    }

    public void update(String article) {
        this.article = article;
    }

    public void updateParent(NoticeReply parent) {
        this.parent = parent;
    }

}
