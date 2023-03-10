package com.hansung.hansungcommunity.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@ToString(callSuper = true)
@Entity
public class FileEntity {

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

    public FileEntity(){}
    public FileEntity(QnaBoard qnaBoard, String originalName, String name, String path) {
        this.qnaBoard = qnaBoard;
        this.originalName = originalName;
        this.name = name;
        this.path = path;
    }

    public static FileEntity of(QnaBoard qnaBoard, String originalName, String name, String path){
        return new FileEntity(qnaBoard,originalName,name,path);
    }


    public void setBoard(QnaBoard qnaBoard){
        this.qnaBoard = qnaBoard;
    }





}
