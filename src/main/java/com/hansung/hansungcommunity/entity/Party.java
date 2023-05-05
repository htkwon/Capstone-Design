package com.hansung.hansungcommunity.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 팀과 유저 간 다대다 관계를 풀기 위한
 * 구인 게시글 관련 엔티티
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Party extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean isApproved;
    private boolean isMeetRequired;
    private boolean isMeetOptional;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "recruit_board_id")
    private RecruitBoard recruitBoard;

    private Party(User user, RecruitBoard recruitBoard, boolean isMeetRequired, boolean isMeetOptional) {
        this.user = user;
        this.recruitBoard = recruitBoard;
        this.isApproved = false;
        this.isMeetRequired = isMeetRequired;
        this.isMeetOptional = isMeetOptional;
    }

    public static Party from(User user, RecruitBoard recruitBoard, boolean isMeetRequired, boolean isMeetOptional) {
        return new Party(user, recruitBoard, isMeetRequired, isMeetOptional);
    }

    // 승인 처리
    public void approve() {
        this.isApproved = true;
    }

}
