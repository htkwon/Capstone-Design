package com.hansung.hansungcommunity.dto.notice;

import com.hansung.hansungcommunity.entity.NoticeBoard;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoticeBoardDto {

    private Long id;
    @NotEmpty
    @Size(min = 3, max = 30)
    private String title;
    @NotEmpty
    private String content;
    private String writer;

    private NoticeBoardDto(Long id, String title, String content, String writer) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }

    public static NoticeBoardDto from(NoticeBoard board) {
        return new NoticeBoardDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getUser().getNickname()
        );
    }

}
