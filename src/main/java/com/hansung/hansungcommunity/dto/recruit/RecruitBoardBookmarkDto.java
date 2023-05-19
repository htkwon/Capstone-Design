package com.hansung.hansungcommunity.dto.recruit;


import com.hansung.hansungcommunity.dto.user.UserBookmarkDto;
import com.hansung.hansungcommunity.entity.RecruitBoardBookmark;
import lombok.Data;

@Data
public class RecruitBoardBookmarkDto {

    private UserBookmarkDto user;
    private RecruitBoardRequestDto recruitBoard;

    public RecruitBoardBookmarkDto(UserBookmarkDto user, RecruitBoardRequestDto recruitBoard) {
        this.user = user;
        this.recruitBoard = recruitBoard;
    }

    public static RecruitBoardBookmarkDto of(RecruitBoardBookmark recruitBoardBookmark) {
        return new RecruitBoardBookmarkDto(
                UserBookmarkDto.of(recruitBoardBookmark.getUser()),
                RecruitBoardRequestDto.of(recruitBoardBookmark.getRecruitBoard())
        );
    }

}
