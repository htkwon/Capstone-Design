package com.hansung.hansungcommunity.dto.notice;

import com.hansung.hansungcommunity.entity.NoticeBoard;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeBoardDetailsDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final Long stuId;
    private final String introduce;
    private final String profileImg;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final int reply;
    private final int bookmark;
    private final int views;

    private NoticeBoardDetailsDto(Long id, String title, String content, String nickname, Long stuId, String introduce, String profileImg, LocalDateTime createdAt, LocalDateTime modifiedAt, int bookmark, int reply, int views) {
        this.id = id;
        this.title= title;
        this.content = content;
        this.writer = nickname;
        this.stuId = stuId;
        this.introduce = introduce;
        this.profileImg = profileImg;
        this.createdDate = createdAt;
        this.modifiedDate = modifiedAt;
        this.bookmark = bookmark;
        this.reply = reply;
        this.views = views;
    }

    public static NoticeBoardDetailsDto from(NoticeBoard noticeBoard){
        return new NoticeBoardDetailsDto(
                noticeBoard.getId(),
                noticeBoard.getTitle(),
                noticeBoard.getContent(),
                noticeBoard.getUser().getNickname(),
                Long.parseLong(noticeBoard.getUser().getStudentId()),
                noticeBoard.getUser().getIntroduce(),
                noticeBoard.getUser().getProfileImg(),
                noticeBoard.getCreatedAt(),
                noticeBoard.getModifiedAt(),
                noticeBoard.getBookmarks().size(),
                noticeBoard.getReplies().size(),
                noticeBoard.getViews()
        );
    }



}
