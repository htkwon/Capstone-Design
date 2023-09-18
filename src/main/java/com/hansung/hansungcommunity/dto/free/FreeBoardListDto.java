package com.hansung.hansungcommunity.dto.free;

import com.hansung.hansungcommunity.dto.ImageDto;
import com.hansung.hansungcommunity.entity.FreeBoard;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class FreeBoardListDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    private String introduce;
    private String stuId;
    private String profileImg;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int bookmark;
    private int reply;
    private int views;

    private List<ImageDto> image;

    private FreeBoardListDto(FreeBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getUser().getNickname();
        this.stuId = board.getUser().getStudentId();
        this.introduce = board.getUser().getIntroduce();
        this.profileImg = board.getUser().getProfileImg();
        this.createdDate = board.getCreatedAt();
        this.modifiedDate = board.getModifiedAt();
        this.bookmark = board.getBookmarks().size();
        this.reply = board.getReplies().size();
        this.views = board.getViews();
        this.image = null;
    }

    public static FreeBoardListDto from(FreeBoard board) {
        return new FreeBoardListDto(board);
    }

    public void setImage(List<ImageDto> image) {
        this.image = image;
    }

}
