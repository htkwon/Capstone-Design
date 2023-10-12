package com.hansung.hansungcommunity.entity;


import com.hansung.hansungcommunity.dto.common.ReplyDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
    @Column
    @Lob
    private String article;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Reply parent;
    @OneToMany(mappedBy = "parent")
    private List<Reply> children = new ArrayList<>();

    protected Reply(User user, Board board, String article) {
        this.user = user;
        this.board = board;
        this.article = article;
    }

    public static Reply of(User user, Board board, ReplyDto dto) {
        return new Reply(
                user,
                board,
                dto.getArticle()
        );
    }

    public void setParent(Reply parent) {
        this.parent = parent;
    }

    public void update(String article) {
        this.article = article;
    }

    public void updateParent(Reply parent) {
        this.parent = parent;
    }

}
