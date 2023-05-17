package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.qna.QnaBoardBookmarkDto;
import com.hansung.hansungcommunity.dto.qna.QnaBoardRequestDto;
import com.hansung.hansungcommunity.dto.user.UserBookmarkDto;
import com.hansung.hansungcommunity.entity.QnaBoard;
import com.hansung.hansungcommunity.entity.QnaBoardBookmark;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.exception.BoardNotFoundException;
import com.hansung.hansungcommunity.exception.UserNotFoundException;
import com.hansung.hansungcommunity.repository.QnaBoardBookmarkRepository;
import com.hansung.hansungcommunity.repository.QnaBoardRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnaBoardBookmarkService {

    private final QnaBoardBookmarkRepository qnaBoardBookmarkRepository;
    private final UserRepository userRepository;
    private final QnaBoardRepository qnaBoardRepository;

    public List<QnaBoardBookmarkDto> getBoards(Long userId) {
        List<QnaBoardBookmark> qnaBoardBookmarks = qnaBoardBookmarkRepository.findAllByUserId(userId);

        return qnaBoardBookmarks.stream()
                .map(bookmark -> {
                    UserBookmarkDto userBookmarkDto = UserBookmarkDto.of(bookmark.getUser());
                    QnaBoardRequestDto qnaBoardRequestDto = QnaBoardRequestDto.of(bookmark.getQnaBoard());
                    return new QnaBoardBookmarkDto(userBookmarkDto, qnaBoardRequestDto);
                }).collect(Collectors.toList());
    }

    @Transactional
    public QnaBoardBookmarkDto create(Long boardId, Long id) {
        QnaBoard qnaBoard = qnaBoardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("북마크 등록 실패, 해당하는 게시글이 없습니다."));
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("북마크 등록 실패, 해당하는 유저가 없습니다."));
        QnaBoardBookmark bookmark = QnaBoardBookmark.of(user, qnaBoard);

        qnaBoardBookmarkRepository.save(bookmark);

        return QnaBoardBookmarkDto.of(bookmark);
    }

    @Transactional
    public void cancel(Long boardId, Long userId) {
        QnaBoardBookmark qnaBoardBookmark = qnaBoardBookmarkRepository.findByQnaBoardIdAndUserId(boardId, userId)
                .orElseThrow(() -> new BoardNotFoundException("북마크 등록 취소 실패, 해당하는 게시글이 없습니다."));

        qnaBoardBookmarkRepository.delete(qnaBoardBookmark);
    }

    public Boolean check(Long boardId, Long userId) {
        QnaBoardBookmark qnaBoardBookmark = qnaBoardBookmarkRepository.findByQnaBoardIdAndUserId(boardId, userId)
                .orElse(null);

        return qnaBoardBookmark != null;
    }

    public int count(Long boardId) {
        List<QnaBoardBookmark> list = qnaBoardBookmarkRepository.findAllByQnaBoardId(boardId);

        return list.size();
    }

}
