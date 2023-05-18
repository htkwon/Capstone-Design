package com.hansung.hansungcommunity.service;


import com.hansung.hansungcommunity.dto.FileDto;
import com.hansung.hansungcommunity.entity.FileEntity;
import com.hansung.hansungcommunity.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FileService {

    private final FileRepository fileRepository;

    @Transactional
    public FileDto save(FileDto dto){
        FileEntity fileEntity = FileEntity.of(dto.getQnaBoard(),dto.getFreeBoard(),dto.getRecruitBoard(),dto.getOriginalName());
        FileEntity res = fileRepository.save(fileEntity);

        return FileDto.from(res);
    }

}
