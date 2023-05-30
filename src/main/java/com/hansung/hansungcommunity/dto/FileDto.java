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

    private String createdName;

    public FileDto(Board board,String originalName,String createdName){
        this.board = board;
        this.originalName = originalName;
        this.createdName = createdName;
    }


    public static FileDto of(Board board,String originalName,String createdName){return new FileDto(board,originalName,createdName);}


    public static FileDto from(FileEntity entity) {
        return new FileDto(
                entity.getBoard(),
                entity.getOriginalName(),
                entity.getCreatedName()
        );
    }

}
