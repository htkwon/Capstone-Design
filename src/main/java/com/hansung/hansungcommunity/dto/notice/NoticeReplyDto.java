package com.hansung.hansungcommunity.dto.notice;

import com.hansung.hansungcommunity.dto.user.UserReplyDto;
import com.hansung.hansungcommunity.entity.NoticeReply;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeReplyDto {

    private Long id;
    @NotEmpty
    private String article;
    private Long parentId;
    private UserReplyDto user;
    private LocalDateTime createdAt;

    private NoticeReplyDto(Long id, String article, UserReplyDto dto, Long parentId, LocalDateTime createdAt) {
        this.id = id;
        this.article = article;
        this.user = dto;
        this.parentId = parentId;
        this.createdAt = createdAt;
    }


    public NoticeReplyDto(NoticeReply noticeReply) {
        this.id = noticeReply.getId();
        this.article = noticeReply.getArticle();
        this.parentId = (noticeReply.getParent() != null) ? noticeReply.getParent().getId() : null;
        this.user = new UserReplyDto(noticeReply.getUser());
        this.parentId = parentId;
        this.createdAt = noticeReply.getCreatedAt();
    }

    public NoticeReplyDto(Long id, String article, UserReplyDto userReplyDto, LocalDateTime createdAt) {
        this.id = id;
        this.article = article;
        this.user = userReplyDto;
        this.createdAt = createdAt;
    }

    public static NoticeReplyDto from(NoticeReply noticeReply, UserReplyDto dto) {
        Long parentId = noticeReply.getParent() != null ? noticeReply.getParent().getId() : null;
        return new NoticeReplyDto(
                noticeReply.getId(),
                noticeReply.getArticle(),
                dto,
                parentId,
                noticeReply.getCreatedAt()
        );
    }


    public static NoticeReplyDto createParent(NoticeReply noticeReply) {
        return new NoticeReplyDto(
                noticeReply.getId(),
                noticeReply.getArticle(),
                new UserReplyDto(noticeReply.getUser()),
                noticeReply.getCreatedAt()
        );
    }

    public static NoticeReplyDto createChildren(NoticeReply noticeReply) {
        return new NoticeReplyDto(
                noticeReply.getId(),
                noticeReply.getArticle(),
                noticeReply.getParent().getId(),
                new UserReplyDto(noticeReply.getUser()),
                noticeReply.getCreatedAt()
        );
    }

    public static List<NoticeReplyDto> listOf(List<NoticeReply> replies) {
        return replies.stream()
                .map(NoticeReplyDto::new)
                .collect(Collectors.toList());
    }

}

