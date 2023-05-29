package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.notice.NoticeReplyDto;
import com.hansung.hansungcommunity.dto.user.UserReplyDto;
import com.hansung.hansungcommunity.entity.NoticeBoard;
import com.hansung.hansungcommunity.entity.NoticeReply;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.exception.BoardNotFoundException;
import com.hansung.hansungcommunity.exception.ParentReplyMismatchException;
import com.hansung.hansungcommunity.exception.ReplyNotFoundException;
import com.hansung.hansungcommunity.exception.UserNotFoundException;
import com.hansung.hansungcommunity.repository.NoticeReplyRepository;
import com.hansung.hansungcommunity.repository.NoticeRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeReplyService {

    private final NoticeReplyRepository noticeReplyRepository;
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    @Transactional
    public NoticeReplyDto create(Long userId, Long boardId, NoticeReplyDto replyDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("댓글 생성 실패, 해당하는 유저가 없습니다."));
        NoticeBoard board = noticeRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("댓글 생성 실패, 해당하는 게시글이 없습니다."));

        NoticeReply parent = null;

        if (replyDto.getParentId() != null) {
            parent = noticeReplyRepository.findById(replyDto.getParentId())
                    .orElseThrow(() -> new ReplyNotFoundException("대댓글 생성 실패, 부모 댓글이 없습니다."));
            if (!parent.getNoticeBoard().getId().equals(boardId)) {
                throw new ParentReplyMismatchException("대댓글 생성 실패, 부모 댓글과 게시글이 다릅니다.");
            }
        }

        NoticeReply noticeReply = NoticeReply.of(user, board, replyDto);

        if (parent != null) {
            noticeReply.updateParent(parent);
        }

        NoticeReply savedReply = noticeReplyRepository.save(noticeReply);
        if (parent != null) {
            return NoticeReplyDto.createChildren(savedReply);
        } else {
            return NoticeReplyDto.createParent(savedReply);
        }

    }

    @Transactional(readOnly = true)
    public List<NoticeReplyDto> getReplyList(Long boardId) {
        NoticeBoard board = noticeRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("댓글 목록 조회 실패, 해당하는 게시글이 없습니다."));
        List<NoticeReply> replies = noticeReplyRepository.findAllWithChildrenByArticleId(board.getId());

        return NoticeReplyDto.listOf(replies);
    }

    @Transactional
    public NoticeReplyDto update(NoticeReplyDto replyDto) {
        NoticeReply reply = noticeReplyRepository.findById(replyDto.getId())
                .orElseThrow(() -> new ReplyNotFoundException("댓글 수정 실패, 해당하는 댓글이 없습니다."));
        if (replyDto.getParentId() != null) {
            NoticeReply parent = noticeReplyRepository.findById(replyDto.getParentId())
                    .orElseThrow(() -> new ReplyNotFoundException("댓글 수정 실패, 해당하는 부모 댓글이 없습니다."));
            reply.setParent(parent);
        } else {
            reply.setParent(null);
        }
        reply.update(replyDto.getArticle());
        UserReplyDto userReplyDto = new UserReplyDto(userRepository.findById(reply.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException("댓글 수정 실패, 해당하는 유저가 없습니다.")));

        return NoticeReplyDto.from(noticeReplyRepository.save(reply), userReplyDto);
    }

    @Transactional
    public void delete(Long replyId) {
        deleteReplyMethod(replyId);
    }

    //재귀 호출로 댓글의 대댓글 및 대댓글의 댓글 있을 시 모두 삭제
    private void deleteReplyMethod(Long replyId) {
        NoticeReply reply = noticeReplyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyNotFoundException("댓글 삭제 실패, 해당하는 댓글이 없습니다."));

        // 댓글의 대댓글 삭제
        List<NoticeReply> children = noticeReplyRepository.findAllByParentId(reply.getId());
        for (NoticeReply child : children) {
            deleteReplyMethod(child.getId());
        }

        // 댓글 삭제
        noticeReplyRepository.delete(reply);
    }

}
