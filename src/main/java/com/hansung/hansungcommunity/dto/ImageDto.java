package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.Image;
import com.hansung.hansungcommunity.entity.QnaBoard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {

    private QnaBoard qnaBoard;
    private String originalName;
    private String name;
    private String path;



    public static ImageDto of(QnaBoard qnaBoard,String originalName, String name, String path){
        return new ImageDto(qnaBoard,originalName,name,path);
    }

    public static ImageDto from(Image entity){
        return new ImageDto(
                entity.getQnaBoard(),
                entity.getOriginalName(),
                entity.getName(),
                entity.getPath()
        );
    }


}
