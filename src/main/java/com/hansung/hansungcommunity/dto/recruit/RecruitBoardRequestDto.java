package com.hansung.hansungcommunity.dto.recruit;

import com.hansung.hansungcommunity.entity.RecruitBoard;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * 구인 게시글 생성 요청 DTO
 */
@Data
@NoArgsConstructor
public class RecruitBoardRequestDto {

    @Size(min = 3, max = 30)
    private String title;
    @NotEmpty
    private String content;
    private String required;
    private String optional;
    @Min(2)
    @Max(10)
    private int party;
    @Min(1)
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
