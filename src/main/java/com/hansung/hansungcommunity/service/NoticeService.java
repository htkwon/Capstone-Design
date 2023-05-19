package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.NoticeBoardDto;
import com.hansung.hansungcommunity.entity.NoticeBoard;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.exception.BoardNotFoundException;
import com.hansung.hansungcommunity.exception.UserNotFoundException;
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
                .stream()
                .flatMap(board -> noticeRepository.findAllById(board.getId()).stream())
                .map(NoticeBoardDto::of)
                .collect(Collectors.toList());
    }

    public NoticeBoardDto detail(Long boardId) {
        return NoticeBoardDto.of(noticeRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("공지사항 조회 실패, 해당하는 공지사항이 없습니다.")));
    }


    @Transactional
    public Long post(NoticeBoardDto dto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("공지사항 작성 실패, 해당하는 관리자가 없습니다."));

        return noticeRepository.save(NoticeBoard.of(dto, user)).getId();
    }

    @Transactional
    public void delete(Long boardId) {
        noticeRepository.delete(noticeRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("공지사항 삭제 실패, 해당하는 공지사항이 없습니다.")));
    }

    @Transactional
    public NoticeBoardDto update(NoticeBoardDto dto, Long boardId) {
        NoticeBoard noticeBoard = noticeRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("공지사항 수정 실패, 해당하는 게시글이 없습니다."));
        noticeBoard.setContent(dto.getContent());
        noticeBoard.setTitle(dto.getTitle());

        return NoticeBoardDto.of(noticeRepository.save(noticeBoard));
    }

    @Transactional
    public Long mappingUser(Long id, NoticeBoard noticeBoard) {
        User user = userRepository.getReferenceById(id);
        noticeBoard.setUser(user);
        noticeRepository.save(noticeBoard);
        return noticeBoard.getId();
    }
}
