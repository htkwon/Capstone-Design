package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@NoArgsConstructor
public class FileDto {

    private Board board;
    private String originalName;


    public FileDto(Board board,String originalName){
        this.board = board;
        this.originalName = originalName;
    }


    public static FileDto of(Board board,String originalName){return new FileDto(board,originalName);}


    public static FileDto from(FileEntity entity) {
        return new FileDto(
                entity.getBoard(),
                entity.getOriginalName()
        );
    }

}
