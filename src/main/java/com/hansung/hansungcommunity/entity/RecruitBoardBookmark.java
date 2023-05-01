package com.hansung.hansungcommunity.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class RecruitBoardBookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="recruit_board_id")
    private RecruitBoard recruitBoard;

    public RecruitBoardBookmark(User user, RecruitBoard recruitBoard){
        this.user = user;
        this.recruitBoard = recruitBoard;
    }
    public static RecruitBoardBookmark of(User user, RecruitBoard recruitBoard){
        return new RecruitBoardBookmark(
                user,
                recruitBoard
        );
    }

}
