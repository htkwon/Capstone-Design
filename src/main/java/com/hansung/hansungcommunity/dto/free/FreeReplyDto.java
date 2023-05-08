package com.hansung.hansungcommunity.dto.free;

import com.hansung.hansungcommunity.dto.qna.QnaReplyDto;
import com.hansung.hansungcommunity.dto.user.UserReplyDto;
import com.hansung.hansungcommunity.entity.FreeReply;
import com.hansung.hansungcommunity.entity.QnaReply;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreeReplyDto {
    private Long id;

    @NotNull
    private String article;

    private Long parentId;
    private UserReplyDto user;
    private LocalDateTime createdAt;



    private FreeReplyDto(Long id, String article,UserReplyDto dto,Long parentId,LocalDateTime createdAt){
        this.id = id;
        this.article = article;
        this.user = dto;
        this.parentId = parentId;
        this.createdAt = createdAt;
    }


    public FreeReplyDto(FreeReply freeReply) {
        this.id = freeReply.getId();
        this.article = freeReply.getArticle();
        this.parentId = (freeReply.getParent()!=null)? freeReply.getParent().getId() : null;
        this.user = new UserReplyDto(freeReply.getUser());
        this.parentId = parentId;
        this.createdAt = freeReply.getCreatedAt();

    }

    public FreeReplyDto(Long id, String article, UserReplyDto userReplyDto, LocalDateTime createdAt) {
        this.id = id;
        this.article = article;
        this.user = userReplyDto;
        this.createdAt = createdAt;
    }

    public static FreeReplyDto from (FreeReply freeReply, UserReplyDto dto){
        return new FreeReplyDto(
                freeReply.getId(),
                freeReply.getArticle(),
                dto,
                freeReply.getParent().getId(),
                freeReply.getCreatedAt()
        );
    }


    public static FreeReplyDto createParent(FreeReply freeReply){
        return new FreeReplyDto(
                freeReply.getId(),
                freeReply.getArticle(),
                new UserReplyDto(freeReply.getUser()),
                freeReply.getCreatedAt()
        );
    }

    public static FreeReplyDto createChildren(FreeReply freeReply){
        return new FreeReplyDto(
                freeReply.getId(),
                freeReply.getArticle(),
                freeReply.getParent().getId(),
                new UserReplyDto(freeReply.getUser()),
                freeReply.getCreatedAt()
        );
    }

    public static List<FreeReplyDto> listOf(List<FreeReply> replies) {
        return replies.stream()
                .map(FreeReplyDto::new)
                .collect(Collectors.toList());
    }

}

