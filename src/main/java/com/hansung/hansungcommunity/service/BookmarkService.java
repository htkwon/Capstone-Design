package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.entity.Board;
import com.hansung.hansungcommunity.entity.Bookmark;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.exception.BoardNotFoundException;
import com.hansung.hansungcommunity.repository.BoardRepository;
import com.hansung.hansungcommunity.repository.BookmarkRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void create(Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("북마크 등록 실패, 해당하는 게시글이 없습니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BoardNotFoundException("북마크 등록 실패, 해당하는 유저가 없습니다."));

        Bookmark bookmark = Bookmark.of(user, board);

        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void cancel(Long boardId, Long userId) {
        Bookmark bookmark = bookmarkRepository.findByBoardIdAndUserId(boardId, userId)
                .orElseThrow(() -> new BoardNotFoundException("북마크 등록 취소 실패, 해당하는 게시글이 없습니다."));

        bookmarkRepository.delete(bookmark);
    }

    public int count(Long boardId) {
        return (int) bookmarkRepository.countByBoardId(boardId);
    }

    public boolean check(Long boardId, Long userId) {
        return bookmarkRepository.existsByBoardIdAndUserId(boardId, userId);
    }

}
