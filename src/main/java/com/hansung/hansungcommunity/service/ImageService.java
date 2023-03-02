package com.hansung.hansungcommunity.service;


import com.hansung.hansungcommunity.dto.ImageDto;
import com.hansung.hansungcommunity.dto.QnaBoardDto;
import com.hansung.hansungcommunity.entity.Image;
import com.hansung.hansungcommunity.entity.QnaBoard;
import com.hansung.hansungcommunity.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ImageService {

    private final ImageRepository imageRepository;

//    @Transactional
//    public ImageDto save(ImageDto dto, QnaBoardDto qDto){
//        QnaBoard entity = qDto.toEntity();
//        Image image = Image.of(entity,dto.getOriginalName(),dto.getName(),dto.getPath());
//
//        Image res = imageRepository.save(image);
//        return ImageDto.from(res);
//    }

    @Transactional
    public ImageDto save(ImageDto dto){
        Image image = Image.of(dto.getQnaBoard(),dto.getOriginalName(),dto.getName(),dto.getPath());
        Image res = imageRepository.save(image);
        return ImageDto.from(res);

    }

}
