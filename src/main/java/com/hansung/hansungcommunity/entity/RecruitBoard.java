package com.hansung.hansungcommunity.entity;

import com.hansung.hansungcommunity.dto.recruit.RecruitBoardRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "recruit_board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RecruitBoard extends Board {

    @Id
    private Long id;
    private String title;
    private String content;
    private String required;
    private String optional;
    @ColumnDefault(value = "0")
    private int bookmarks;
    private int party; // 모집할 인원 수
    private int gathered; // 모집된 인원 수
    private boolean isCompleted; // 모집 완료 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stu_id")
    private User user;

    @OneToMany(mappedBy = "recruitBoard")
    private List<Party> parties = new ArrayList<>();

    // 테스트용, 추후 인자 추가 등 작업 요망
    private RecruitBoard(String title, String content, String required, String optional, int party, int gathered) {
        this.title = title;
        this.content = content;
        this.required = required;
        this.optional = optional;
        this.party = party;
        this.gathered = gathered;
        this.isCompleted = party <= gathered;
    }

    public static RecruitBoard createBoard(RecruitBoardRequestDto dto, User user) {
        RecruitBoard board = new RecruitBoard(
                dto.getTitle(),
                dto.getContent(),
                dto.getRequired(),
                dto.getOptional(),
                dto.getParty(),
                dto.getGathered()
        );
        board.setUser(user);

        return board;
    }

    // 연관관계 메소드
    public void setUser(User user) {
        this.user = user;
        user.getPostRecruitBoards().add(this);
    }

//    public void patch(RecruitBoardDto dto) {
//        if (dto.getTitle() != null)
//            this.title = dto.getTitle();
//
//        if (dto.getContent() != null)
//            this.content = dto.getContent();
//
//        modified();
//    }

    // 조회수 증가 메소드
    public void increaseHits() {
        increaseViews();
    }

    public void increaseGathered() {
        this.gathered++;
    }

    public void updateIsCompleted() {
        if (this.gathered >= this.party) {
            this.isCompleted = true;
        }
    }

}
