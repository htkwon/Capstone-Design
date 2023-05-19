package com.hansung.hansungcommunity.dto.recruit;

import com.hansung.hansungcommunity.entity.RecruitBoard;
import lombok.Data;

/**
 * 구인 게시글 생성 요청 DTO
 * 추후 필드 추가 등 작업 요망
 */

@Data
public class RecruitBoardRequestDto {

    private String title;
    private String content;
    private String required;
    private String optional;
    private int party;
    private int gathered;

    public RecruitBoardRequestDto(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static RecruitBoardRequestDto of(RecruitBoard recruitBoard) {
        return new RecruitBoardRequestDto(
                recruitBoard.getTitle(),
                recruitBoard.getContent()
        );
    }

}
