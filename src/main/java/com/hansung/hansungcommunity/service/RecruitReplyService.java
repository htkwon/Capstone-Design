package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.recruit.RecruitReplyDto;
import com.hansung.hansungcommunity.dto.user.UserReplyDto;
import com.hansung.hansungcommunity.entity.RecruitBoard;
import com.hansung.hansungcommunity.entity.RecruitReply;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.repository.RecruitBoardRepository;
import com.hansung.hansungcommunity.repository.RecruitReplyRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitReplyService {

    private final RecruitReplyRepository recruitReplyRepository;
    private final UserRepository userRepository;
    private final RecruitBoardRepository recruitBoardRepository;

    @Transactional
    public RecruitReplyDto create(Long userId, Long boardId, RecruitReplyDto replyDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new IllegalArgumentException("댓글 생성 실패, 해당 유저가 없습니다."));
        RecruitBoard board = recruitBoardRepository.findById(boardId)
                .orElseThrow(()->new IllegalArgumentException("댓글 생성 실패, 해당 게시글이 없습니다."));

        RecruitReply parent = null;

        if(replyDto.getParentId() != null){
            parent = recruitReplyRepository.findById(replyDto.getParentId())
                    .orElseThrow(()-> new IllegalArgumentException("대댓글 생성 실패, 부모 댓글이 없습니다."));
            if(!parent.getRecruitBoard().getId().equals(boardId)){
                throw new IllegalArgumentException("대댓글 생성 실패, 부모 댓글과 게시글이 다릅니다.");
            }
        }

        RecruitReply recruitReply = RecruitReply.of(user, board, replyDto);

        if(parent != null){
            recruitReply.updateParent(parent);
        }
        RecruitReply savedReply = recruitReplyRepository.save(recruitReply);
        if(parent != null){
            return RecruitReplyDto.createChildren(savedReply);
        }else{
            return RecruitReplyDto.createParent(savedReply);
        }
    }

    @Transactional(readOnly = true)
    public List<RecruitReplyDto> getReplyList(Long boardId){
        RecruitBoard board = recruitBoardRepository.findById(boardId)
                .orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없습니다."));
        List<RecruitReply> replies = recruitReplyRepository.findAllWithChildrenByArticleId(board.getId());
        return RecruitReplyDto.listOf(replies);
    }

    @Transactional
    public RecruitReplyDto update(RecruitReplyDto replyDto){
        RecruitReply reply = recruitReplyRepository.findById(replyDto.getId())
                .orElseThrow(()-> new IllegalArgumentException("해당 댓글이 없습니다."));
        if(replyDto.getParentId() != null) {
            RecruitReply parent = recruitReplyRepository.findById(replyDto.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("해당 부모 댓글이 없습니다."));
            reply.setParent(parent);
        }else {
            reply.setParent(null);
        }
        reply.update(replyDto.getArticle());
        UserReplyDto userReplyDto = new UserReplyDto(userRepository.findById(reply.getUser().getId())
                .orElseThrow(()-> new IllegalArgumentException("해당 유저가 없습니다.")));
        return RecruitReplyDto.from(recruitReplyRepository.save(reply),userReplyDto);

    }

    @Transactional
    public void delete(Long replyId){
        deleteReplyMethod(replyId);
    }

    private void deleteReplyMethod(Long replyId){
        RecruitReply reply = recruitReplyRepository.findById(replyId)
                .orElseThrow(()-> new IllegalArgumentException("해당 댓글이 없습니다."));
        List<RecruitReply> children = recruitReplyRepository.findAllByParentId(reply.getId());
        for(RecruitReply child : children){
            deleteReplyMethod(child.getId());
        }
        recruitReplyRepository.delete(reply);
    }



}
