package com.hansung.hansungcommunity.dto.qna;

import com.hansung.hansungcommunity.dto.ImageDto;
import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;


@Getter
public class QnaBoardListDto {

    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final String introduce;
    private final String profileImg;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final String language;
    private final int bookmark;
    private final int reply;
    private final int views;
    private final String stuId;
    private List<ImageDto> image;

    private QnaBoardListDto(Long id, String title, String content, String writer, String introduce, String profileImg, LocalDateTime createdDate, LocalDateTime modifiedDate, String language, int bookmark, int reply, int views, String stuId, List<ImageDto> images) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.writer = writer;
        this.introduce = introduce;
        this.profileImg = profileImg;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.language = language;
        this.bookmark = bookmark;
        this.reply = reply;
        this.views = views;
        this.stuId = stuId;
        this.image = images;
    }

    public static QnaBoardListDto from(QnaBoard board){
        return new QnaBoardListDto(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getUser().getNickname(),
                board.getUser().getIntroduce(),
                board.getUser().getProfileImg(),
                board.getCreatedAt(),
                board.getModifiedAt(),
                board.getLanguage(),
                board.getBookmarks().size(),
                board.getReplies().size(),
                board.getViews(),
                board.getUser().getStudentId(),
                null
        );
    }
    public void setImage(List<ImageDto> image){
        this.image = image;
    }

}
