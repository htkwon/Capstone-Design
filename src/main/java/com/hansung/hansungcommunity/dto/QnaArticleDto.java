package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.QnaArticle;
import com.hansung.hansungcommunity.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class QnaArticleDto {

    private Long id;
    private User user;
    private String title;
    private String content;
    private int point;
    private String tag;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


    //Testcode 및 생성의 편의를 위한 Factory method
    public static QnaArticleDto of(Long id,User user, String title, String content, int point, String tag, LocalDateTime createdAt,LocalDateTime modifiedAt){
        return new QnaArticleDto(id,user,title,content,point,tag,createdAt,modifiedAt);
    }

    public static QnaArticleDto from(QnaArticle entity){
        return new QnaArticleDto(
                entity.getId(),
                entity.getUser(),
                entity.getTitle(),
                entity.getContent(),
                entity.getPoint(),
                entity.getTag(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }
    public QnaArticle toEntity(){
        return QnaArticle.of(
                user,
                title,
                content,
                tag,
                point
        );
    }




}
