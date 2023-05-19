package com.hansung.hansungcommunity.entity;


import com.hansung.hansungcommunity.dto.recruit.RecruitReplyDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "recruit_reply")
@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecruitReply extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_reply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruit_board_id")
    private RecruitBoard recruitBoard;

    @Column
    private String article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private RecruitReply parent;

    @OneToMany(mappedBy = "parent")
    private List<RecruitReply> children = new ArrayList<>();

    private RecruitReply(User user, RecruitBoard recruitBoard, String article) {
        this.user = user;
        this.recruitBoard = recruitBoard;
        this.article = article;
    }

    public static RecruitReply of(User user, RecruitBoard board, RecruitReplyDto dto) {
        return new RecruitReply(
                user,
                board,
                dto.getArticle()
        );
    }

    public void update(String article) {
        this.article = article;
    }

    public void updateParent(RecruitReply parent) {
        this.parent = parent;
    }

}
