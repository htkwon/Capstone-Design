package com.hansung.hansungcommunity.dto.media;

import lombok.Getter;

@Getter
public class ImageDto {

    private String imageUrl;

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
