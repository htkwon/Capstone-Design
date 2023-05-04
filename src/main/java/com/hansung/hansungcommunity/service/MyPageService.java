package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.user.UserActivityDto;
import com.hansung.hansungcommunity.entity.*;
import com.hansung.hansungcommunity.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {
    private final FreeReplyRepository freeReplyRepository;
    private final QnaReplyRepository qnaReplyRepository;
    private final RecruitReplyRepository recruitReplyRepository;
    private final BoardRepository boardRepository;
    private final FreeBoardRepository freeBoardRepository;
    private final QnaBoardRepository qnaBoardRepository;
    private final RecruitBoardRepository recruitBoardRepository;
    private final FreeBoardBookmarkRepository freeBoardBookmarkRepository;
    private final QnaBoardBookmarkRepository qnaBoardBookmarkRepository;
    private final RecruitBoardBookmarkRepository recruitBoardBookmarkRepository;


    /**
     * 해당 유저가 댓글 단 게시글을 최신 순으로
     *  1. 작성한 댓글을 보이게 할 것인지?
     *  2. 작성한 댓글이 있는 게시글을 보여줄 것인지?
     */
    public List<UserActivityDto> getReplyList(Long userId){
        List<UserActivityDto> userActivityDtos = boardRepository.findAll()
                .stream()
                .map(board -> {
                    if(board.getBoardType().equals("FreeBoard")){
                        FreeReply freeReply = freeReplyRepository.findByFreeBoardId(board.getId())
                                .orElse(null);
                        if(freeReply!=null && freeReply.getUser().getId().equals(userId)){
                            return UserActivityDto.of(freeReply.getFreeBoard());
                        }
                    }else if(board.getBoardType().equals("QnaBoard")){
                        QnaReply qnaReply = qnaReplyRepository.findByBoardId(board.getId())
                                .orElse(null);
                        if(qnaReply!=null && qnaReply.getUser().getId().equals(userId)){
                            return UserActivityDto.of(qnaReply.getBoard());
                        }
                    }else{
                        RecruitReply recruitReply = recruitReplyRepository.findByRecruitBoardId(board.getId())
                                .orElse(null);
                        if(recruitReply!=null && recruitReply.getUser().getId().equals(userId)){
                            return UserActivityDto.of(recruitReply.getRecruitBoard());
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(UserActivityDto :: getCreatedDate).reversed())
                .collect(Collectors.toList());
        return userActivityDtos;
    }


    /**
     * 해당 접속 유저가 작성한 게시글 반환 (최신순서)
     */
    public List<UserActivityDto> getBoardList(Long userId) {
        List<UserActivityDto> userActivityDtos = boardRepository.findAllByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Mypage - Board 에러"))
                .stream()
                .map(board -> {
                    if (board.getBoardType().equals("FreeBoard")) {
                        FreeBoard freeBoard = freeBoardRepository.findById(board.getId())
                                .orElseThrow(() -> new IllegalArgumentException("Mypage - FreeBoard 에러"));
                        return UserActivityDto.of(freeBoard);
                    } else if (board.getBoardType().equals("QnaBoard")) {
                        QnaBoard qnaBoard = qnaBoardRepository.findById(board.getId())
                                .orElseThrow(() -> new IllegalArgumentException("Mypage - QnaBoard 에러"));
                        return UserActivityDto.of(qnaBoard);
                    } else {
                        RecruitBoard recruitBoard = recruitBoardRepository.findById(board.getId())
                                .orElseThrow(() -> new IllegalArgumentException("Mypage - Recruit 에러"));
                        return UserActivityDto.of(recruitBoard);
                    }
                })
                .sorted(Comparator.comparing(UserActivityDto::getCreatedDate).reversed())
                .collect(Collectors.toList());

        return userActivityDtos;
    }


    /**
     * 해당 접속 유저가 북마크한 게시글 반환 (최신순서)
    */
    public List<UserActivityDto> getBookmarkList(Long userId) {
        List<UserActivityDto> userActivityDtos = boardRepository.findAll()
                .stream()
                .map(board -> {
                    if (board.getBoardType().equals("FreeBoard")) {
                        FreeBoardBookmark freeBoardBookmark = freeBoardBookmarkRepository.findById(board.getId())
                                .orElse(null);
                        if (freeBoardBookmark != null && freeBoardBookmark.getUser().getId().equals(userId)) {
                            return UserActivityDto.of(freeBoardRepository.findById(freeBoardBookmark.getId())
                                    .orElseThrow(() -> new IllegalArgumentException("Mypage - 에러")));
                        }
                    } else if (board.getBoardType().equals("QnaBoard")) {
                        QnaBoardBookmark qnaBoardBookmark = qnaBoardBookmarkRepository.findById(board.getId())
                                .orElse(null);
                        if (qnaBoardBookmark != null && qnaBoardBookmark.getUser().getId().equals(userId)) {
                            return UserActivityDto.of(qnaBoardRepository.findById(qnaBoardBookmark.getId())
                                    .orElseThrow(() -> new IllegalArgumentException("Mypage - 에러")));
                        }
                    } else {
                        RecruitBoardBookmark recruitBoardBookmark = recruitBoardBookmarkRepository.findById(board.getId())
                                .orElse(null);
                        if(recruitBoardBookmark!=null && recruitBoardBookmark.getUser().getId().equals(userId)){
                            return UserActivityDto.of(recruitBoardRepository.findById(recruitBoardBookmark.getId())
                                    .orElseThrow(()->new IllegalArgumentException("Mypage - 에러")));
                        }
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(UserActivityDto::getCreatedDate).reversed())
                .collect(Collectors.toList());
        return userActivityDtos;
    }


}
