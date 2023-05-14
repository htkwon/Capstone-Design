package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.FreeBoard;
import com.hansung.hansungcommunity.entity.QnaBoard;
import com.hansung.hansungcommunity.entity.RecruitBoard;
import lombok.Data;

@Data
public class AdminBoardDto {
    private Long id;
    private String title;
    private String boardType;

    public AdminBoardDto(Long id, String title,String boardType) {
        this.id = id;
        this.title = title;
        this.boardType = boardType;
    }

    public static AdminBoardDto of(FreeBoard board){
        return new AdminBoardDto(
                board.getId(),
                board.getTitle(),
                board.getBoardType()
         );
    }
    public static AdminBoardDto of(QnaBoard board){
        return new AdminBoardDto(
                board.getId(),
                board.getTitle(),
                board.getBoardType()
        );
    }
    public static AdminBoardDto of(RecruitBoard board){
        return new AdminBoardDto(
                board.getId(),
                board.getTitle(),
                board.getBoardType()
        );
    }

}
