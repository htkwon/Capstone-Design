package com.hansung.hansungcommunity.dto.media;

import com.hansung.hansungcommunity.entity.Board;
import com.hansung.hansungcommunity.entity.FileEntity;
import lombok.Getter;

@Getter
public class FileDto {

    private final Board board;
    private final String originalName;
    private final String createdName;

    private FileDto(Board board, String originalName, String createdName) {
        this.board = board;
        this.originalName = originalName;
        this.createdName = createdName;
    }


    public static FileDto of(Board board, String originalName, String createdName) {
        return new FileDto(board, originalName, createdName);
    }


    public static FileDto from(FileEntity entity) {
        return new FileDto(
                entity.getBoard(),
                entity.getOriginalName(),
                entity.getCreatedName()
        );
    }

}
