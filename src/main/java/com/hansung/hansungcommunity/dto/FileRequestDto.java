package com.hansung.hansungcommunity.dto;

import com.hansung.hansungcommunity.entity.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileRequestDto {

    private String originalName;


    public static FileRequestDto of(FileEntity entity) {
        return new FileRequestDto(
                entity.getOriginalName()
        );
    }

}
