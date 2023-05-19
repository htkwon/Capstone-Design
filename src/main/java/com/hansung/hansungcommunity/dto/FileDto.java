package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {

    private QnaBoard qnaBoard;

    private FreeBoard freeBoard;

    private RecruitBoard recruitBoard;

    private NoticeBoard noticeBoard;
    private String originalName;

    public FileDto(QnaBoard qnaBoard, String originalName) {
        this.qnaBoard = qnaBoard;
        this.originalName = originalName;
    }

    public FileDto(FreeBoard freeBoard, String originalName) {
        this.freeBoard = freeBoard;
        this.originalName = originalName;
    }

    public FileDto(RecruitBoard recruitBoard, String originalName) {
        this.recruitBoard = recruitBoard;
        this.originalName = originalName;
    }

    public FileDto(NoticeBoard noticeBoard,String originalName){
        this.noticeBoard = noticeBoard;
        this.originalName = originalName;
    }


    public static FileDto of(QnaBoard qnaBoard, String originalName) {
        return new FileDto(qnaBoard, originalName);
    }

    public static FileDto of(FreeBoard freeBoard, String originalName) {
        return new FileDto(freeBoard, originalName);
    }

    public static FileDto of(RecruitBoard recruitBoard, String originalName) {
        return new FileDto(recruitBoard, originalName);
    }

    public static FileDto of(NoticeBoard noticeBoard,String originalName){return new FileDto(noticeBoard,originalName);}

    public static FileDto from(FileEntity entity) {
        return new FileDto(
                entity.getQnaBoard(),
                entity.getFreeBoard(),
                entity.getRecruitBoard(),
                entity.getNoticeBoard(),
                entity.getOriginalName()
        );
    }

}
