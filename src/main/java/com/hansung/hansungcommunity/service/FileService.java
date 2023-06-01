package com.hansung.hansungcommunity.service;


import com.hansung.hansungcommunity.dto.FileDto;
import com.hansung.hansungcommunity.dto.FileRequestDto;
import com.hansung.hansungcommunity.entity.FileEntity;
import com.hansung.hansungcommunity.exception.BoardNotFoundException;
import com.hansung.hansungcommunity.repository.BoardRepository;
import com.hansung.hansungcommunity.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FileService {

    private final FileRepository fileRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public FileDto save(FileDto dto) {
        FileEntity fileEntity = FileEntity.of(dto.getBoard(), dto.getOriginalName(), dto.getCreatedName());
        FileEntity res = fileRepository.save(fileEntity);

        return FileDto.from(res);
    }

    public Boolean check(Long boardId) {
        return fileRepository.findAllByBoard(boardRepository.findById(boardId)
                        .orElseThrow(() -> new BoardNotFoundException("해당 게시글이 없습니다.")))
                .stream()
                .anyMatch(Objects::nonNull);
    }

    public List<FileRequestDto> list(Long boardId) {
        return fileRepository.findAllByBoard(boardRepository.findById(boardId)
                        .orElseThrow(() -> new BoardNotFoundException("해당 게시글이 없습니다.")))
                .stream()
                .map(FileRequestDto::of)
                .collect(Collectors.toList());
    }

    public String getCreatedName(String imageName) {
        FileEntity fileEntity = fileRepository.findByOriginalName(imageName);
        return fileEntity.getCreatedName();
    }


    @Transactional
    public void delete(String imageName) {
        fileRepository.deleteByCreatedName(imageName);
    }
}
