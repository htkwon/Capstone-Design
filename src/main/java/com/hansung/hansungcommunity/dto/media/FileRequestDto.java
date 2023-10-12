package com.hansung.hansungcommunity.dto.media;

import com.hansung.hansungcommunity.entity.FileEntity;
import lombok.Getter;

@Getter
public class FileRequestDto {

    private final String originalName;

    public FileRequestDto(String originalName){this.originalName = originalName;}

    public static FileRequestDto from(FileEntity entity) {
        return new FileRequestDto(
                entity.getOriginalName()
        );
    }

}
