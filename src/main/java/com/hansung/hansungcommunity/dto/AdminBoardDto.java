package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.FreeBoard;
import com.hansung.hansungcommunity.entity.QnaBoard;
import com.hansung.hansungcommunity.entity.RecruitBoard;
import lombok.Data;
import lombok.Getter;

@Getter
public class AdminBoardDto {
    
    private final Long id;
    private final String title;
    private final String boardType;

    private AdminBoardDto(Long id, String title, String boardType) {
        this.id = id;
        this.title = title;
        this.boardType = boardType;
    }

    public static AdminBoardDto from(FreeBoard board) {
        return new AdminBoardDto(
                board.getId(),
                board.getTitle(),
                board.getBoardType()
        );
    }

    public static AdminBoardDto from(QnaBoard board) {
        return new AdminBoardDto(
                board.getId(),
                board.getTitle(),
                board.getBoardType()
        );
    }

    public static AdminBoardDto from(RecruitBoard board) {
        return new AdminBoardDto(
                board.getId(),
                board.getTitle(),
                board.getBoardType()
        );
    }

}
