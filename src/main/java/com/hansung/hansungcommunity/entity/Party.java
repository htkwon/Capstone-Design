package com.hansung.hansungcommunity.entity;

import lombok.Getter;

import javax.persistence.*;

/**
 * 팀과 유저 간 다대다 관계를 풀기 위한
 * 구인 게시글 관련 엔티티
 */

@Entity
@Getter
public class Party {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "recruit_board_id")
    private RecruitBoard recruitBoard;

}
