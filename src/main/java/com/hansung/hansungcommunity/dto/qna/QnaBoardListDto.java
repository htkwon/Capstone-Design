package com.hansung.hansungcommunity.dto.qna;

import com.hansung.hansungcommunity.dto.ImageDto;
import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class QnaBoardListDto {

    private Long id;
    private String title;
    private String content;
    private String writer;
    //TODO : 파일
    private String introduce;
    private String profileImg;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private String language;
    private int bookmark;
    private int reply;
    private int views;
    private String stuId;

    private List<ImageDto> image;

    public QnaBoardListDto(QnaBoard board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getUser().getNickname();
        this.introduce = board.getUser().getIntroduce();
        this.createdDate = board.getCreatedAt();
        this.modifiedDate = board.getModifiedAt();
        this.language = board.getLanguage();
        this.profileImg = board.getUser().getProfileImg();
        this.bookmark = board.getBookmarks().size();
        this.reply = board.getReplies().size();
        this.views = board.getViews();
        this.stuId = board.getUser().getStudentId();
        this.image = null;
    }

}
