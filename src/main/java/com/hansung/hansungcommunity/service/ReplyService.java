package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.common.ReplyDto;
import com.hansung.hansungcommunity.dto.user.UserReplyDto;
import com.hansung.hansungcommunity.entity.Board;
import com.hansung.hansungcommunity.entity.Reply;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.exception.BoardNotFoundException;
import com.hansung.hansungcommunity.exception.ParentReplyMismatchException;
import com.hansung.hansungcommunity.exception.ReplyNotFoundException;
import com.hansung.hansungcommunity.exception.UserNotFoundException;
import com.hansung.hansungcommunity.repository.BoardRepository;
import com.hansung.hansungcommunity.repository.ReplyRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReplyService {

    private final ReplyRepository replyRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReplyDto createReply(Long userId, Long boardId, ReplyDto replyDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("댓글 생성 실패, 해당하는 유저가 없습니다."));
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("댓글 생성 실패, 해당하는 게시글이 없습니다."));

        Reply parent = null;

        if (replyDto.getParentId() != null) {
            parent = replyRepository.findById(replyDto.getParentId())
                    .orElseThrow(() -> new ReplyNotFoundException("대댓글 생성 실패, 부모 댓글이 없습니다."));
            if (!parent.getBoard().getId().equals(boardId)) {
                throw new ParentReplyMismatchException("대댓글 생성 실패, 부모 댓글과 게시글이 다릅니다.");
            }
        }

        Reply reply = Reply.of(user, board, replyDto);

        if (parent != null) {
            reply.updateParent(parent);
        }

        Reply savedReply = replyRepository.save(reply);
        if (parent != null) {
            return ReplyDto.createChildren(savedReply);
        } else {
            return ReplyDto.createParent(savedReply);
        }

    }


    @Transactional(readOnly = true)
    public List<ReplyDto> getListOfReply(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("댓글 목록 조회 실패, 해당하는 게시글이 없습니다."));
        List<Reply> replies = replyRepository.findAllWithChildrenByArticleId(board.getId());

        return ReplyDto.listOf(replies);
    }

    @Transactional
    public ReplyDto updateReply(ReplyDto replyDto) {
        Reply reply = replyRepository.findById(replyDto.getId())
                .orElseThrow(() -> new ReplyNotFoundException("댓글 수정 실패, 해당하는 댓글이 없습니다."));
        if (replyDto.getParentId() != null) {
            Reply parent = replyRepository.findById(replyDto.getParentId())
                    .orElseThrow(() -> new ReplyNotFoundException("댓글 수정 실패, 해당하는 부모 댓글이 없습니다."));
            reply.setParent(parent);
        } else {
            reply.setParent(null);
        }
        reply.update(replyDto.getArticle());
        UserReplyDto userReplyDto = UserReplyDto.from(userRepository.findById(reply.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException("댓글 수정 실패, 해당하는 유저가 없습니다.")));

        return ReplyDto.of(replyRepository.save(reply), userReplyDto);
    }

    @Transactional
    public void deleteReply(Long replyId) {
        deleteReplyMethod(replyId);
    }

    //재귀 호출로 댓글의 대댓글 및 대댓글의 댓글 있을 시 모두 삭제
    private void deleteReplyMethod(Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyNotFoundException("댓글 삭제 실패, 해당하는 댓글이 없습니다."));

        // 댓글의 대댓글 삭제
        List<Reply> children = replyRepository.findAllByParentId(reply.getId());
        for (Reply child : children) {
            deleteReplyMethod(child.getId());
        }

        // 댓글 삭제
        replyRepository.delete(reply);
    }

}
