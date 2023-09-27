package com.hansung.hansungcommunity.dto.notice;

import com.hansung.hansungcommunity.entity.NoticeBoard;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class NoticeBoardMainDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final String stuId;
    private final String introduce;
    private final String profileImg;
    private final LocalDateTime createdDate;
    private final int bookmark;
    private final int reply;

    private NoticeBoardMainDto(Long id, String title, String content, String writer, String stuId, String introduce, String profileImg, LocalDateTime createdDate, int bookmark, int reply) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.stuId = stuId;
        this.introduce = introduce;
        this.profileImg = profileImg;
        this.createdDate = createdDate;
        this.bookmark = bookmark;
        this.reply = reply;
    }

    public static NoticeBoardMainDto from(NoticeBoard board){
        return new NoticeBoardMainDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getUser().getNickname(),
                board.getUser().getStudentId(),
                board.getUser().getIntroduce(),
                board.getUser().getProfileImg(),
                board.getCreatedAt(),
                board.getBookmarks().size(),
                board.getReplies().size()
        );
    }

}
