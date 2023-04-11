package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.free.FreeReplyDto;
import com.hansung.hansungcommunity.entity.FreeBoard;
import com.hansung.hansungcommunity.entity.FreeReply;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.repository.FreeBoardRepository;
import com.hansung.hansungcommunity.repository.FreeReplyRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FreeReplyService {

    private final FreeReplyRepository freeReplyRepository;
    private final FreeBoardRepository freeBoardRepository;
    private final UserRepository userRepository;


    @Transactional
    public FreeReplyDto create(Long userId, Long boardId, FreeReplyDto replyDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패, 해당 유저가 없습니다."));
        FreeBoard board = freeBoardRepository.findById(boardId)
                .orElseThrow(()->new IllegalArgumentException("댓글 생성 실패, 해당 게시글이 없습니다."));

        FreeReply parent = null;

        if(replyDto.getParentId() != null){
            parent = freeReplyRepository.findById(replyDto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("대댓글 생성 실패, 부모 댓글이 없습니다."));
            if(!parent.getBoard().getId().equals(boardId)){
                throw new IllegalArgumentException("대댓글 생성 실패, 부모 댓글과 게시글이 다릅니다.");
            }
        }

        FreeReply freeReply = FreeReply.of(user, board, replyDto);

        if(parent != null){
            freeReply.updateParent(parent);
        }

        FreeReply savedReply = freeReplyRepository.save(freeReply);
        if(parent !=null){
            return FreeReplyDto.createChildren(savedReply);
        }else {
            return FreeReplyDto.createParent(savedReply);
        }

    }


    @Transactional(readOnly = true)
    public List<FreeReplyDto> getReplyList(Long boardId) {
        FreeBoard board = freeBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다."));
        List<FreeReply> replies = freeReplyRepository.findAllWithChildrenByArticleId(board.getId());
       return FreeReplyDto.listOf(replies);

    }

    @Transactional
    public void update(FreeReplyDto replyDto) {
        FreeReply reply = freeReplyRepository.findById(replyDto.getId())
                .orElseThrow(()->new IllegalArgumentException("해당 댓글이 없습니다."));
        reply.update(replyDto.getArticle());
        freeReplyRepository.save(reply);

    }
    @Transactional
    public void delete(Long replyId) {
        FreeReply reply = freeReplyRepository.findById(replyId)
                .orElseThrow(()-> new IllegalArgumentException("해당 댓글이 없습니다."));
        freeReplyRepository.delete(reply);
    }
}
