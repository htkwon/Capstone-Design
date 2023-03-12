package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.FileEntity;
import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {

    private QnaBoard qnaBoard;
    private String originalName;
    private String name;
    private String path;



    public static FileDto of(QnaBoard qnaBoard, String originalName, String name, String path){
        return new FileDto(qnaBoard,originalName,name,path);
    }

    public static FileDto from(FileEntity entity){
        return new FileDto(
                entity.getQnaBoard(),
                entity.getOriginalName(),
                entity.getName(),
                entity.getPath()
        );
    }


}
