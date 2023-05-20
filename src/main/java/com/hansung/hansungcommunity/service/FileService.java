package com.hansung.hansungcommunity.service;


import com.hansung.hansungcommunity.dto.FileDto;
import com.hansung.hansungcommunity.dto.FileRequestDto;
import com.hansung.hansungcommunity.entity.FileEntity;
import com.hansung.hansungcommunity.exception.BoardNotFoundException;
import com.hansung.hansungcommunity.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class FileService {

    private final FileRepository fileRepository;
    private final FreeBoardRepository freeBoardRepository;
    private final QnaBoardRepository qnaBoardRepository;
    private final RecruitBoardRepository recruitBoardRepository;

    private final NoticeRepository noticeRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public FileDto save(FileDto dto) {
        FileEntity fileEntity = FileEntity.of(dto.getBoard(), dto.getOriginalName());
        FileEntity res = fileRepository.save(fileEntity);

        return FileDto.from(res);
    }

    public Boolean check(Long boardId) {
        return fileRepository.findAllByBoard(boardRepository.findById(boardId)
                        .orElseThrow(() -> new BoardNotFoundException("해당 게시글이 없습니다.")))
                .stream()
                .anyMatch(file -> file != null);

    }

    public List<FileRequestDto> list(Long boardId) {
        return fileRepository.findAllByBoard(boardRepository.findById(boardId)
                        .orElseThrow(() -> new BoardNotFoundException("해당 게시글이 없습니다.")))
                .stream()
                .map(FileRequestDto::of)
                .collect(Collectors.toList());
    }
}
