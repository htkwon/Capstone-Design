package com.hansung.hansungcommunity.entity;

import com.hansung.hansungcommunity.dto.recruit.RecruitBoardRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "recruit_board")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class RecruitBoard extends Board {

    @Id
    private Long id;
    private String required;
    private String optional;
    private int party; // 모집할 인원 수
    private int gathered; // 모집된 인원 수
    private boolean isCompleted; // 모집 완료 여부

    @OneToMany(mappedBy = "recruitBoard", cascade = CascadeType.REMOVE)
    private List<Party> parties = new ArrayList<>();

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
        super.setUser(user);
    }

    // 조회수 증가 메소드
    public void increaseHits() {
        increaseViews();
    }

    // 자동 모집 완료 처리
    public void updateIsCompleted(Long count) {
        this.isCompleted = this.gathered + count >= this.party;
    }

    public void patch(RecruitBoardRequestDto dto) {
        if (dto.getTitle() != null)
            this.title = dto.getTitle();

        if (dto.getContent() != null)
            this.content = dto.getContent();

        if (dto.getOptional() != null)
            this.optional = dto.getOptional();

        this.party = dto.getParty();

        modified();
    }

    // 작성자에 의한 모집 완료 처리
    public void complete() {
        this.isCompleted = true;
    }

}
