package com.hansung.hansungcommunity.dto.notice;

import com.hansung.hansungcommunity.dto.media.ImageDto;
import com.hansung.hansungcommunity.entity.NoticeBoard;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
public class NoticeBoardListDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final String stuId;
    private final String introduce;
    private final String profileImg;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final int bookmark;
    private final int reply;
    private final int views;
    private List<ImageDto> image;


    public NoticeBoardListDto(Long id, String title, String content, String writer, String stuId, String introduce, String profileImg, LocalDateTime createdDate, LocalDateTime modifiedDate, int bookmark, int reply, int views, List<ImageDto> image) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.stuId = stuId;
        this.introduce = introduce;
        this.profileImg = profileImg;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.bookmark = bookmark;
        this.reply = reply;
        this.views = views;
        this.image = image;
    }

    public static NoticeBoardListDto from(NoticeBoard board){
        return new NoticeBoardListDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getUser().getNickname(),
                board.getUser().getStudentId(),
                board.getUser().getIntroduce(),
                board.getUser().getProfileImg(),
                board.getCreatedAt(),
                board.getModifiedAt(),
                board.getBookmarks().size(),
                board.getReplies().size(),
                board.getViews(),
                null
        );
    }
    public void setImage(List<ImageDto> image) {this.image = image;}

}
