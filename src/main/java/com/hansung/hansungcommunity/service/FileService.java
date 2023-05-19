package com.hansung.hansungcommunity.service;


import com.hansung.hansungcommunity.dto.FileDto;
import com.hansung.hansungcommunity.dto.FileRequestDto;
import com.hansung.hansungcommunity.entity.FileEntity;
import com.hansung.hansungcommunity.entity.RecruitBoard;
import com.hansung.hansungcommunity.repository.FileRepository;
import com.hansung.hansungcommunity.repository.FreeBoardRepository;
import com.hansung.hansungcommunity.repository.QnaBoardRepository;
import com.hansung.hansungcommunity.repository.RecruitBoardRepository;
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

    @Transactional
    public FileDto save(FileDto dto) {
        FileEntity fileEntity = FileEntity.of(dto.getQnaBoard(), dto.getFreeBoard(), dto.getRecruitBoard(), dto.getOriginalName());
        FileEntity res = fileRepository.save(fileEntity);

        return FileDto.from(res);
    }

    public Boolean check(Long boardId, String boardTpye) {
        if (boardTpye.equals("free")) {
            return fileRepository.findAllByFreeBoard(freeBoardRepository.findById(boardId)
                            .orElseThrow(() -> new IllegalArgumentException("해당 자유게시글이 없습니다.")))
                    .stream()
                    .anyMatch(file -> file != null);
        } else if (boardTpye.equals("questions")) {
            return fileRepository.findAllByQnaBoard(qnaBoardRepository.findById(boardId)
                            .orElseThrow(() -> new IllegalArgumentException("해당 qna게시글이 없습니다.")))
                    .stream()
                    .anyMatch(file -> file != null);
        } else {
            return fileRepository.findAllByRecruitBoard(recruitBoardRepository.findById(boardId)
                            .orElseThrow(() -> new IllegalArgumentException("해당 구인게시글이 없습니다.")))
                    .stream()
                    .anyMatch(file -> file != null);
        }

    }

    public List<FileRequestDto> list(Long boardId, String boardType) {
        if (boardType.equals("free")) {
            return fileRepository.findAllByFreeBoard(freeBoardRepository.findById(boardId)
                            .orElseThrow(() -> new IllegalArgumentException("해당 자유게시글이 없습니다.")))
                    .stream()
                    .map(FileRequestDto::of)
                    .collect(Collectors.toList());
        } else if (boardType.equals("questions")) {
            return fileRepository.findAllByQnaBoard(qnaBoardRepository.findById(boardId)
                            .orElseThrow(() -> new IllegalArgumentException("해당 qna게시글이 없습니다.")))
                    .stream()
                    .map(FileRequestDto::of)
                    .collect(Collectors.toList());
        } else {
            return fileRepository.findAllByRecruitBoard(recruitBoardRepository.findById(boardId)
                            .orElseThrow(() -> new IllegalArgumentException("해당 구인게시글이 없습니다.")))
                    .stream()
                    .map(FileRequestDto::of)
                    .collect(Collectors.toList());
        }
    }
}
