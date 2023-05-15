package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.dto.user.UserRequestDto;
import com.hansung.hansungcommunity.entity.NoticeBoard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NoticeBoardDto {

    private Long id;
    private String title;
    private String content;

    private String writer;


    public NoticeBoardDto(Long id, String title, String content,String writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }


    public static NoticeBoardDto of(NoticeBoard board){
        return new NoticeBoardDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getUser().getNickname()
        );
    }


}
