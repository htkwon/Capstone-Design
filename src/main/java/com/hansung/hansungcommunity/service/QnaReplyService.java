package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.auth.CustomAuthentication;
import com.hansung.hansungcommunity.dto.qna.QnaReplyAdoptCheckDto;
import com.hansung.hansungcommunity.dto.qna.QnaReplyDto;
import com.hansung.hansungcommunity.dto.user.UserReplyDto;
import com.hansung.hansungcommunity.entity.*;
import com.hansung.hansungcommunity.exception.*;
import com.hansung.hansungcommunity.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QnaReplyService {

    private final QnaReplyRepository qnaReplyRepository;
    private final QnaBoardRepository boardRepository;
    private final UserRepository userRepository;
    private final AdoptRepository adoptRepository;
    private final ReplyRepository replyRepository;

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
        Optional<Adopt> adopt = adoptRepository.findByQnaBoardId(boardId);
        if (adopt.isEmpty()) {
            return QnaReplyAdoptCheckDto.of(false, null);
        }
        Reply reply = replyRepository.findByBoardIdAndUserId(boardId, adopt.get().getUser().getId());
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

        return QnaReplyDto.of(qnaReplyRepository.save(reply), userReplyDto);
    }

    @Transactional
    public boolean adopt(Long replyId, Long userId) {
        QnaReply reply = qnaReplyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyNotFoundException("댓글 채택 실패, 해당하는 댓글이 없습니다."));

        if (userId.equals(reply.getBoard().getUser().getId())) {
            adoptRepository.save(Adopt.of(reply.getBoard(), reply.getUser()));
            return true;
        }
        return false;
    }

    @Transactional
    public Long cancel(Long replyId) {
        QnaReply reply = qnaReplyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyNotFoundException("댓글 채택 취소 실패, 해당하는 댓글이 없습니다."));
        QnaBoard qnaBoard = boardRepository.findById(reply.getBoard().getId())
                .orElseThrow(() -> new BoardNotFoundException("댓글 채택 취소 실패, 해당하는 게시글이 없습니다."));
        Adopt adopt = adoptRepository.findByQnaBoardId(qnaBoard.getId())
                .orElseThrow(() -> new AdoptNotFoundException("댓글 채택 취소 실패, 해당하는 채택 정보가 없습니다."));

        qnaReplyRepository.save(reply);
        adoptRepository.delete(adopt);

        return reply.getId();
    }

    @Transactional
    public void delete(Long replyId) {
        deleteReplyMethod(replyId);
    }

    private void deleteReplyMethod(Long replyId) {
        QnaReply qnaReply = qnaReplyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyNotFoundException("댓글 삭제 실패, 해당하는 댓글이 없습니다."));
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyNotFoundException("댓글 삭제 실패, 해당하는 댓글이 없습니다."));

        Optional<Adopt> adopt = adoptRepository.findByQnaBoardIdAndUserId(reply.getBoard().getId(), reply.getUser().getId());
        if (!adopt.isEmpty()) adoptRepository.delete(adopt.get());

        List<QnaReply> children = qnaReplyRepository.findAllByParentId(qnaReply.getId());
        for (QnaReply child : children) {
            deleteReplyMethod(child.getId());
        }
        qnaReplyRepository.delete(qnaReply);
    }

}
