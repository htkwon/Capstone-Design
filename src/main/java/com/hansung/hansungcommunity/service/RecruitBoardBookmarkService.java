package com.hansung.hansungcommunity.service;


import com.hansung.hansungcommunity.dto.recruit.RecruitBoardBookmarkDto;
import com.hansung.hansungcommunity.dto.recruit.RecruitBoardRequestDto;
import com.hansung.hansungcommunity.dto.user.UserBookmarkDto;
import com.hansung.hansungcommunity.entity.RecruitBoard;
import com.hansung.hansungcommunity.entity.RecruitBoardBookmark;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.exception.BoardNotFoundException;
import com.hansung.hansungcommunity.exception.BookmarkNotFoundException;
import com.hansung.hansungcommunity.exception.UserNotFoundException;
import com.hansung.hansungcommunity.repository.RecruitBoardBookmarkRepository;
import com.hansung.hansungcommunity.repository.RecruitBoardRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitBoardBookmarkService {

    private final RecruitBoardBookmarkRepository recruitBoardBookmarkRepository;
    private final RecruitBoardRepository recruitBoardRepository;
    private final UserRepository userRepository;

    public List<RecruitBoardBookmarkDto> getBoards(Long userId) {
        List<RecruitBoardBookmark> recruitBoardBookmarks = recruitBoardBookmarkRepository.findAllByUserId(userId);

        return recruitBoardBookmarks.stream()
                .map(bookmark -> {
                    UserBookmarkDto userBookmarkDto = UserBookmarkDto.of(bookmark.getUser());
                    RecruitBoardRequestDto recruitBoardRequestDto = RecruitBoardRequestDto.of(bookmark.getRecruitBoard());
                    return new RecruitBoardBookmarkDto(userBookmarkDto, recruitBoardRequestDto);
                }).collect(Collectors.toList());
    }

    @Transactional
    public RecruitBoardBookmarkDto create(Long boardId, Long id) {
        RecruitBoard recruitBoard = recruitBoardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("북마크 등록 실패, 해당 게시글이 없습니다."));
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("북마크 등록 실패, 해당 유저가 없습니다."));
        RecruitBoardBookmark bookmark = RecruitBoardBookmark.of(user, recruitBoard);

        recruitBoardBookmarkRepository.save(bookmark);

        return RecruitBoardBookmarkDto.of(bookmark);
    }

    @Transactional
    public void cancel(Long boardId, Long id) {
        RecruitBoardBookmark recruitBoardBookmark = recruitBoardBookmarkRepository.findByRecruitBoardIdAndUserId(boardId, id)
                .orElseThrow(() -> new BookmarkNotFoundException("북마크 등록 취소 실패, 해당하는 북마크 정보가 없습니다."));

        recruitBoardBookmarkRepository.delete(recruitBoardBookmark);
    }

    public Boolean check(Long boardId, Long id) {
        RecruitBoardBookmark recruitBoardBookmark = recruitBoardBookmarkRepository.findByRecruitBoardIdAndUserId(boardId, id)
                .orElse(null);

        return recruitBoardBookmark != null;
    }

    public int count(Long boardId) {
        List<RecruitBoardBookmark> list = recruitBoardBookmarkRepository.findAllByRecruitBoardId(boardId);

        return list.size();
    }
}
