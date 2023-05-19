package com.hansung.hansungcommunity.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.aspectj.weaver.ast.Not;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@ToString(callSuper = true)
@Entity
@NoArgsConstructor
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="qna_board_id")
    private QnaBoard qnaBoard;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="free_board_id")
    private FreeBoard freeBoard;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="recruit_board_id")
    private RecruitBoard recruitBoard;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="notice_board_id")
    private NoticeBoard noticeBoard;

    @NotNull private String originalName;

    public FileEntity(QnaBoard qnaBoard, FreeBoard freeBoard, RecruitBoard recruitBoard, NoticeBoard noticeBoard, String originalName) {
        this.qnaBoard = qnaBoard;
        this.freeBoard = freeBoard;
        this.recruitBoard = recruitBoard;
        this.noticeBoard = noticeBoard;
        this.originalName = originalName;
    }

    public static FileEntity of(QnaBoard qnaBoard,FreeBoard freeBoard,RecruitBoard recruitBoard,NoticeBoard noticeBoard, String originalName){
        return new FileEntity(qnaBoard,freeBoard,recruitBoard,noticeBoard,originalName);
    }

    public void setQnaBoard(QnaBoard qnaBoard){
        this.qnaBoard = qnaBoard;
    }
    public void setFreeBoard(FreeBoard freeBoard){this.freeBoard = freeBoard;}
    public void setRecruitBoard(RecruitBoard recruitBoard){this.recruitBoard = recruitBoard;}

    public void setNoticeBoard(NoticeBoard noticeBoard){this.noticeBoard = noticeBoard;}

}
