package com.hansung.hansungcommunity.entity;


import com.hansung.hansungcommunity.dto.QnaArticleDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)
/*
@Table(indexes ={
    @Index(name="title",columnList = "title"),
    @Index(name="tag",columnList = "tag"),
    @Index(name="createdAt",columnList = "createdAt")
})*/
@Entity
public class QnaArticle extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "qna_article_id")
    private Long id;

    @ToString.Exclude
    @JoinColumn(name = "stu_id")
    @ManyToOne(fetch = FetchType.LAZY )
    private User user;

    @NotNull
    private String title;
    @NotNull
    private String content;  //TODO: length 설정하기
    //@NotNull
    private int point;
    @Column
    private String tag;

    @ColumnDefault(value = "0") private int hits;
    @ColumnDefault(value = "0") private int bookmarkHits;
    @ColumnDefault(value = "0") private int report;

    /*
    TODO: 이미지 저장 필드 추후 개발
    */

    public QnaArticle(){}

    public QnaArticle(User user,String title, String content, String tag, int point) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.point = point;

    }

    public QnaArticle(String title, String content) {
        this.title = title;
        this.content = content;

    }

    //추후 다른 곳에서(EX.. Test)에서 편하게 만들기위해 Factory method 사용
    public static QnaArticle of(User user,String title, String content, String tag, int point){
        return new QnaArticle(user,title,content,tag,point);
    }

    public static QnaArticle of(String title, String content) {
        return new QnaArticle(title,content);
    }

    public void updateArticle(QnaArticleDto dto){
        if(dto.getTitle()!=null) this.title = dto.getTitle();
        if(dto.getContent()!=null) this.content = dto.getContent();
        this.tag = dto.getTag();
        this.point = dto.getPoint();
    }
    public void setUser(User user){
        this.user = user;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QnaArticle)) return false;
        QnaArticle that = (QnaArticle) o;
        return id!=null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
