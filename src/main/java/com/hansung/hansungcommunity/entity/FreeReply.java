package com.hansung.hansungcommunity.entity;


import com.hansung.hansungcommunity.dto.free.FreeReplyDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "free_reply")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FreeReply extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "free_reply_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "free_board_id")
    private FreeBoard board;

    @Column
    private String article;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private FreeReply parent;

    @OneToMany(mappedBy = "parent")
    private List<FreeReply> children = new ArrayList<>();


    private FreeReply( User user, FreeBoard board, String article){
        this.user = user;
        this.board = board;
        this.article = article;
    }

    public static FreeReply of(User user, FreeBoard board, FreeReplyDto dto){
        return new FreeReply(
                user,
                board,
                dto.getArticle()
        );
    }
    public  void update(String article){
        this.article = article;
    }

    public void updateParent(FreeReply parent){
        this.parent = parent;
    }



}
