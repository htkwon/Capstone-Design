package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 메인 페이지 인기 게시글 조회 DTO
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardMainDto {

    private Long id;
    private String title;
    private String boardType;

    public static BoardMainDto from(Board board) {
        return new BoardMainDto(
                board.getId(),
                board.getTitle(),
                typeConvert(board.getBoardType())
        );
    }

    private static String typeConvert(String type) {
        if (type.equals("FreeBoard")) {
            return "free";
        } else if (type.equals("QnaBoard")) {
            return "questions";
        } else {
            return "recruit";
        }
    }

}
