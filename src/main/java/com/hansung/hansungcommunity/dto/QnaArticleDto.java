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
    private String title;
    private String content;
    private int point;
    private String tag;



    //Testcode 및 생성의 편의를 위한 Factory method
    public static QnaArticleDto of(Long id,String title, String content, int point, String tag){
        return new QnaArticleDto(id,title,content,point,tag);
    }

    public static QnaArticleDto from(QnaArticle entity){
        return new QnaArticleDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getPoint(),
                entity.getTag()
        );
    }

    public QnaArticle toEntity(){
        return QnaArticle.of(
                title,
                content
        );
    }







}
