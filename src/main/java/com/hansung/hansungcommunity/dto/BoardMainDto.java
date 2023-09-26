package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.Board;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 메인 페이지 인기 게시글 조회 DTO
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BoardMainDto {

    private final Long id;
    private final String title;
    private final String boardType;

    public static BoardMainDto from(Board board) {
        return new BoardMainDto(
                board.getId(),
                board.getTitle(),
                typeConvert(board.getBoardType())
        );
    }

    private static String typeConvert(String type) {
        switch (type) {
            case "FreeBoard":
                return "free";
            case "QnaBoard":
                return "questions";
            case "RecruitBoard":
                return "recruit";
            default:
                return "notice";
        }
    }

}
