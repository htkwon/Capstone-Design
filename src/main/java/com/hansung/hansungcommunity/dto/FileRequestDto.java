package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
