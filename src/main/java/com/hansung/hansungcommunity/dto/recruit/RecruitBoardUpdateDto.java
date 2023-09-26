package com.hansung.hansungcommunity.dto.recruit;

import com.hansung.hansungcommunity.entity.RecruitBoard;
import lombok.Getter;

/**
 * 게시글 수정 시, 기존 게시글 정보를 담아주는 DTO
 */
@Getter
public class RecruitBoardUpdateDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String required;
    private final String optional;
    private final int party;
    private int gathered;

    private RecruitBoardUpdateDto(RecruitBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.required = board.getRequired();
        this.optional = board.getOptional();
        this.party = board.getParty();
    }

    public static RecruitBoardUpdateDto from(RecruitBoard board) {
        return new RecruitBoardUpdateDto(board);
    }

    public void setGathered(int gathered) {
        this.gathered = gathered;
    }

}
