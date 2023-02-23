package com.hansung.hansungcommunity.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@ToString(callSuper = true)
@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="qna_board_id")
    private QnaBoard qnaBoard;



    @NotNull private String originalName;
    @NotNull private String name;
    @NotNull private String path;

    public Image(){}
    public Image(QnaBoard qnaBoard,String originalName, String name, String path) {
        this.qnaBoard = qnaBoard;
        this.originalName = originalName;
        this.name = name;
        this.path = path;
    }

    public static Image of(QnaBoard qnaBoard,String originalName, String name, String path){
        return new Image(qnaBoard,originalName,name,path);
    }


    public void setBoard(QnaBoard qnaBoard){
        this.qnaBoard = qnaBoard;
    }





}
