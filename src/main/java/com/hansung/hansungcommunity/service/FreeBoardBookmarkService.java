package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.free.FreeBoardBookmarkDto;
import com.hansung.hansungcommunity.dto.free.FreeBoardRequestDto;
import com.hansung.hansungcommunity.dto.user.UserBookmarkDto;
import com.hansung.hansungcommunity.entity.FreeBoard;
import com.hansung.hansungcommunity.entity.FreeBoardBookmark;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.exception.BoardNotFoundException;
import com.hansung.hansungcommunity.repository.FreeBoardBookmarkRepository;
import com.hansung.hansungcommunity.repository.FreeBoardRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FreeBoardBookmarkService {
    private final FreeBoardBookmarkRepository freeBoardBookmarkRepository;
    private final UserRepository userRepository;
    private final FreeBoardRepository freeBoardRepository;

    public List<FreeBoardBookmarkDto> getBoards(Long userId) {
        List<FreeBoardBookmark> freeBoardBookmarks = freeBoardBookmarkRepository.findAllByUserId(userId);

        return freeBoardBookmarks.stream()
                .map(bookmark -> {
                    UserBookmarkDto userBookmarkDto = UserBookmarkDto.of(bookmark.getUser());
                    FreeBoardRequestDto freeBoardRequestDto = FreeBoardRequestDto.of(bookmark.getFreeBoard());

                    return new FreeBoardBookmarkDto(userBookmarkDto, freeBoardRequestDto);
                }).collect(Collectors.toList());
    }

    @Transactional
    public FreeBoardBookmarkDto create(Long boardId, Long id) {
        FreeBoard freeBoard = freeBoardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("북마크 등록 실패, 해당하는 게시글이 없습니다."));
        User user = userRepository.getReferenceById(id);

        FreeBoardBookmark bookmark = FreeBoardBookmark.of(user, freeBoard);

        freeBoardBookmarkRepository.save(bookmark);

        return FreeBoardBookmarkDto.of(bookmark);
    }

    @Transactional
    public void cancel(Long boardId, Long userId) {
        FreeBoardBookmark freeBoardBookmark = freeBoardBookmarkRepository.findByFreeBoardIdAndUserId(boardId, userId)
                .orElseThrow(() -> new BoardNotFoundException("북마크 등록 취소 실패, 해당하는 게시글이 없습니다."));

        freeBoardBookmarkRepository.delete(freeBoardBookmark);
    }

    public int count(Long boardId) {
        List<FreeBoardBookmark> list = freeBoardBookmarkRepository.findAllByFreeBoardId(boardId);

        return list.size();
    }

    public Boolean check(Long boardId, Long userId) {
        FreeBoardBookmark freeBoardBookmark = freeBoardBookmarkRepository.findByFreeBoardIdAndUserId(boardId, userId)
                .orElse(null);

        return freeBoardBookmark != null;
    }

}
