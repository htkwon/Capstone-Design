package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.qna.QnaReplyAdoptCheckDto;
import com.hansung.hansungcommunity.dto.qna.QnaReplyDto;
import com.hansung.hansungcommunity.dto.user.UserReplyDto;
import com.hansung.hansungcommunity.entity.Adopt;
import com.hansung.hansungcommunity.entity.QnaBoard;
import com.hansung.hansungcommunity.entity.QnaReply;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.exception.*;
import com.hansung.hansungcommunity.repository.AdoptRepository;
import com.hansung.hansungcommunity.repository.QnaBoardRepository;
import com.hansung.hansungcommunity.repository.QnaReplyRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnaReplyService {

    private final QnaReplyRepository qnaReplyRepository;
    private final QnaBoardRepository boardRepository;
    private final UserRepository userRepository;
    private final AdoptRepository adoptRepository;

    /**
     * 댓글 생성
     * DTO parentId 필드 null 여부로 부모, 자식 결정
     */
    @Transactional
    public QnaReplyDto create(Long userId, Long boardId, QnaReplyDto replyDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("댓글 생성 실패, 해당하는 유저가 없습니다."));

        QnaBoard board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException("댓글 생성 실패, 해당하는 게시글이 없습니다."));

        QnaReply parent = null;

        if (replyDto.getParentId() != null) { // 자식 댓글인 경우
            parent = qnaReplyRepository.findById(replyDto.getParentId()) // 부모 댓글 지정
                    .orElseThrow(() -> new ReplyNotFoundException("대댓글 생성 실패, 부모 댓글이 없습니다."));

            if (!parent.getBoard().getId().equals(boardId)) {
                throw new ParentReplyMismatchException("대댓글 생성 실패, 부모 댓글과 게시글이 다릅니다.");
            }
        }

        QnaReply qnaReply = QnaReply.of(user, board, replyDto); // 부모 댓글 없이 생성 후

        if (parent != null) { // 부모 댓글이 있다면
            qnaReply.updateParent(parent); // 부모 댓글 지정
        }

        QnaReply savedReply = qnaReplyRepository.save(qnaReply);

        if (parent != null) { // 부모 댓글이 있다면
            return QnaReplyDto.createChildren(savedReply); // 자식 댓글
        } else {
            return QnaReplyDto.createParent(savedReply); // 부모 댓글
        }
    }

    @Transactional(readOnly = true)
    public QnaReplyAdoptCheckDto adoptCheck(Long boardId) {
        QnaReply reply = qnaReplyRepository.findFirstByBoardIdAndAdoptTrue(boardId);

        if (reply == null) {
            return QnaReplyAdoptCheckDto.of(false, null);
        }
        return QnaReplyAdoptCheckDto.of(true, reply.getId());
    }

    @Transactional
    public QnaReplyDto update(QnaReplyDto replyDto) {
        QnaReply reply = qnaReplyRepository.findById(replyDto.getId())
                .orElseThrow(() -> new ReplyNotFoundException("댓글 수정 실패, 해당하는 댓글이 없습니다."));
        if (replyDto.getParentId() != null) {
            QnaReply parent = qnaReplyRepository.findById(replyDto.getParentId())
                    .orElseThrow(() -> new ReplyNotFoundException("댓글 수정 실패, 해당하는 부모 댓글이 없습니다."));
            reply.setParent(parent);
        } else {
            reply.setParent(null);
        }
        reply.update(replyDto.getArticle());
        UserReplyDto userReplyDto = UserReplyDto.from(userRepository.findById(reply.getUser().getId())
                .orElseThrow(() -> new UserNotFoundException("댓글 수정 실패, 해당하는 유저가 없습니다.")));

        return QnaReplyDto.from(qnaReplyRepository.save(reply), userReplyDto);
    }

    @Transactional
    public Boolean adopt(Long replyId) {
        QnaReply reply = qnaReplyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyNotFoundException("댓글 채택 실패, 해당하는 댓글이 없습니다."));
        reply.adopt(true);
        qnaReplyRepository.save(reply);
        adoptRepository.save(Adopt.of(reply.getBoard(), reply.getUser()));

        return reply.getAdopt();
    }

    @Transactional
    public Long cancel(Long replyId) {
        QnaReply reply = qnaReplyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyNotFoundException("댓글 채택 취소 실패, 해당하는 댓글이 없습니다."));
        QnaBoard qnaBoard = boardRepository.findById(reply.getBoard().getId())
                .orElseThrow(() -> new BoardNotFoundException("댓글 채택 취소 실패, 해당하는 게시글이 없습니다."));
        Adopt adopt = adoptRepository.findByQnaBoardId(qnaBoard.getId())
                .orElseThrow(() -> new AdoptNotFoundException("댓글 채택 취소 실패, 해당하는 채택 정보가 없습니다."));

        reply.adopt(false);
        qnaReplyRepository.save(reply);
        adoptRepository.delete(adopt);

        return reply.getId();
    }

    @Transactional
    public void delete(Long replyId) {
        deleteReplyMethod(replyId);
    }

    private void deleteReplyMethod(Long replyId) {
        QnaReply reply = qnaReplyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyNotFoundException("댓글 삭제 실패, 해당하는 댓글이 없습니다."));
        List<QnaReply> children = qnaReplyRepository.findAllByParentId(reply.getId());
        for (QnaReply child : children) {
            deleteReplyMethod(child.getId());
        }

        qnaReplyRepository.delete(reply);
    }

}
