package com.hansung.hansungcommunity.dto.recruit;

import com.hansung.hansungcommunity.dto.user.UserReplyDto;
import com.hansung.hansungcommunity.entity.FreeReply;
import com.hansung.hansungcommunity.entity.RecruitReply;
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
public class RecruitReplyDto {

    private Long id;

    @NotNull
    private String article;

    private Long parentId;
    private UserReplyDto user;
    private LocalDateTime createdAt;

    private RecruitReplyDto(Long id,Long parentId, String article,UserReplyDto dto,LocalDateTime createdAt){
        this.id = id;
        this.parentId = parentId;
        this.article = article;
        this.user = dto;
        this.createdAt = createdAt;
    }

    public RecruitReplyDto(Long id, String article, UserReplyDto userReplyDto, LocalDateTime createdAt) {
        this.id= id;
        this.article = article;
        this.user = userReplyDto;
        this.createdAt = createdAt;
    }

    public RecruitReplyDto(RecruitReply recruitReply) {
        this.id = recruitReply.getId();
        this.article = recruitReply.getArticle();
        this.parentId = (recruitReply.getParent()!=null)? recruitReply.getParent().getId() : null;
        this.user = new UserReplyDto(recruitReply.getUser());
        this.createdAt = recruitReply.getCreatedAt();

    }



    public static RecruitReplyDto from (RecruitReply recruitReply, UserReplyDto dto){
        Long parentId  = recruitReply.getParent() != null ? recruitReply.getParent().getId() : null;
        return new RecruitReplyDto(
                recruitReply.getId(),
                recruitReply.getArticle(),
                parentId,
                dto,
                recruitReply.getCreatedAt()
        );
    }

    public static RecruitReplyDto createParent(RecruitReply recruitReply){
        return new RecruitReplyDto(
                recruitReply.getId(),
                recruitReply.getArticle(),
                new UserReplyDto(recruitReply.getUser()),
                recruitReply.getCreatedAt()
        );
    }

    public static RecruitReplyDto createChildren(RecruitReply recruitReply){
        return new RecruitReplyDto(
                recruitReply.getId(),
                recruitReply.getArticle(),
                recruitReply.getParent().getId(),
                new UserReplyDto(recruitReply.getUser()),
                recruitReply.getCreatedAt()
        );
    }

    public static List<RecruitReplyDto> listOf(List<RecruitReply> replies){
        return replies.stream()
                .map(RecruitReplyDto :: new)
                .collect(Collectors.toList());
    }


}
