package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.NoticeBoardDto;
import com.hansung.hansungcommunity.entity.NoticeBoard;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.repository.BoardRepository;
import com.hansung.hansungcommunity.repository.NoticeRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    public List<NoticeBoardDto> getList(Long id) {
        return boardRepository.findAllByUserId(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 작성한 글이 없습니다."))
                .stream()
                .flatMap(board -> noticeRepository.findAllById(board.getId()).stream())
                .map(NoticeBoardDto::of)
                .collect(Collectors.toList());
    }

    public NoticeBoardDto detail(Long boardId) {
        return NoticeBoardDto.of(noticeRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다.")));
    }


    @Transactional
    public NoticeBoardDto post(NoticeBoardDto dto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("해당 유저가 없습니다."));
        return NoticeBoardDto.of(noticeRepository.save(NoticeBoard.of(dto,user)));


    }

    @Transactional
    public void delete(Long boardId) {
        noticeRepository.delete(noticeRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다.")));

    }


    @Transactional
    public NoticeBoardDto update(NoticeBoardDto dto, Long boardId) {
        NoticeBoard noticeBoard = noticeRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습ㄴ다."));
        noticeBoard.setContent(dto.getContent());
        noticeBoard.setTitle(dto.getTitle());
        return NoticeBoardDto.of(noticeRepository.save(noticeBoard));
    }
}
