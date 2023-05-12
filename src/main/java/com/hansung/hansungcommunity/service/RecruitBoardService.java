package com.hansung.hansungcommunity.service;

import com.hansung.hansungcommunity.dto.ImageDto;
import com.hansung.hansungcommunity.dto.recruit.*;
import com.hansung.hansungcommunity.entity.Party;
import com.hansung.hansungcommunity.entity.RecruitBoard;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.repository.PartyRepository;
import com.hansung.hansungcommunity.repository.RecruitBoardRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitBoardService {

    private final RecruitBoardRepository recruitBoardRepository;
    private final UserRepository userRepository;
    private final PartyRepository partyRepository;

    /**
     * 게시글 저장
     */
    @Transactional
    public Long post(Long userId, RecruitBoardRequestDto dto) {
        User user = userRepository.getReferenceById(userId);
        RecruitBoard saved = recruitBoardRepository.save(RecruitBoard.createBoard(dto, user));

        return saved.getId();
    }

    public List<RecruitBoardListDto> getList(Pageable pageable, String search) {
        Pageable setPage = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.Direction.DESC, "createdAt");
        Page<RecruitBoard> page;

        if (search == null) {
            page = recruitBoardRepository.findAll(setPage);
        } else {
            page = recruitBoardRepository.findAllWithSearchParam(search, setPage);
        }

        return page.getContent()
                .stream()
                .map(recruitBoard -> {
                    Long count = partyRepository.countByRecruitBoardIdAndIsApprovedTrue(recruitBoard.getId());
                    RecruitBoardListDto dto = RecruitBoardListDto.from(recruitBoard);
                    dto.setGathered((int) (recruitBoard.getGathered() + count));
                    dto.setImage(extractImagesFromContent(recruitBoard.getContent()));

                    return dto;
                })
                .collect(Collectors.toList());
    }

    public RecruitBoardDetailDto getDetail(Long boardId) {
        RecruitBoard recruitBoard = recruitBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 구인 게시글이 없습니다."));
        Long count = partyRepository.countByRecruitBoardIdAndIsApprovedTrue(boardId);

        RecruitBoardDetailDto dto = RecruitBoardDetailDto.from(recruitBoard);
        dto.setGathered((int) (recruitBoard.getGathered() + count));

        return dto;
    }

    /**
     * 조회수 증가 로직
     */
    @Transactional
    public void increaseHits(Long boardId) {
        RecruitBoard board = recruitBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("조회수 증가 실패, 해당하는 게시글이 없음"));

        board.increaseHits();
    }

    /**
     * 팀 소속 신청
     */
    @Transactional
    public Long apply(Long boardId, Long userId, RecruitBoardApplyRequestDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("신청 실패, 해당하는 유저가 없음"));
        RecruitBoard board = recruitBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("신청 실패, 해당하는 게시글이 없음"));
        Optional<Party> check = partyRepository.findByUserIdAndRecruitBoardId(userId, boardId);

        if (board.isCompleted()) {
            throw new IllegalArgumentException("신청 실패, 모집이 마감된 게시글입니다.");
        }

        if (board.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("신청 실패, 게시글 작성자는 신청이 불가능합니다.");
        }

        if (check.isEmpty()) {
            Party party;
            if (dto.getIsMeetOptional() == null) {
                party = partyRepository.save(Party.from(user, board, dto.getIsMeetRequired(), null));
            } else {
                party = partyRepository.save(Party.from(user, board, dto.getIsMeetRequired(), dto.getIsMeetOptional()));
            }
            return party.getId();
        } else {
            return check.get().getId();
        }
    }

    /**
     * 팀 소속 신청 취소
     */
    @Transactional
    public boolean cancelApplication(Long boardId, Long userId) {
        Party party = partyRepository.findByUserIdAndRecruitBoardId(userId, boardId)
                .orElseThrow(() -> new IllegalArgumentException("신청 취소 실패, 해당 신청 정보가 없습니다."));
        RecruitBoard recruitBoard = party.getRecruitBoard();

        if (!party.isApproved() && !recruitBoard.isCompleted()) {
            partyRepository.delete(party);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 특정 사용자에 대한 신청 승인
     */
    @Transactional
    public boolean approve(Long boardId, Long userId, Long targetUserId) {
        RecruitBoard board = recruitBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("신청 승인 실패, 해당하는 게시글이 없음"));

        if (board.getUser().getId().equals(userId) && !board.isCompleted()) {
            Party party = partyRepository.findByUserIdAndRecruitBoardId(targetUserId, boardId)
                    .orElseThrow(() -> new IllegalArgumentException("신청 승인 실패, 해당하는 신청 정보가 없음"));

            if (!party.isApproved()) {
                party.approve();
                partyRepository.flush();
                Long count = partyRepository.countByRecruitBoardIdAndIsApprovedTrue(boardId); // 해당 게시글의 승인 인원
                board.updateIsCompleted(count);
            }

            return party.isApproved();
        } else {
            throw new IllegalArgumentException("신청 승인 실패, 게시글을 작성한 유저가 아니거나 팀 구성이 완료된 게시글입니다.");
        }
    }

    /**
     * 특정 사용자에 대한 신청 승인 취소
     */
    @Transactional
    public boolean disapprove(Long boardId, Long userId, Long targetUserId) {
        RecruitBoard board = recruitBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("신청 승인 취소 실패, 해당하는 게시글이 없음"));

        if (board.getUser().getId().equals(userId) && !board.isCompleted()) {
            Party party = partyRepository.findByUserIdAndRecruitBoardId(targetUserId, boardId)
                    .orElseThrow(() -> new IllegalArgumentException("신청 승인 취소 실패, 해당하는 신청 정보가 없음"));

            if (party.isApproved()) {
                party.disapprove();
                partyRepository.flush();
                Long count = partyRepository.countByRecruitBoardIdAndIsApprovedTrue(boardId); // 해당 게시글의 승인 인원
                board.updateIsCompleted(count);
            }

            return party.isApproved();
        } else {
            throw new IllegalArgumentException("신청 승인 취소 실패, 게시글을 작성한 유저가 아닙니다.");
        }
    }

    /**
     * 게시글 수정 시, 기존 게시글 정보 반환
     */
    public RecruitBoardUpdateDto getDetailForUpdate(Long boardId) {
        RecruitBoard recruitBoard = recruitBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 구인 게시글이 없습니다."));
        Long count = partyRepository.countByRecruitBoardIdAndIsApprovedTrue(boardId);

        RecruitBoardUpdateDto dto = new RecruitBoardUpdateDto(recruitBoard);
        dto.setGathered((int) (recruitBoard.getGathered() + count));

        return dto;
    }

    /**
     * 게시글 수정
     */
    @Transactional
    public Long update(Long boardId, RecruitBoardRequestDto dto) {
        RecruitBoard recruitBoard = recruitBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 수정 실패, 해당하는 구인 게시글이 없습니다."));

        recruitBoard.patch(dto);

        return boardId;
    }

    /**
     * 게시글 삭제
     */
    @Transactional
    public void delete(Long boardId) {
        recruitBoardRepository.deleteById(boardId);
    }

    /**
     * 전체 게시글 수
     */
    public long getTotal() {
        return recruitBoardRepository.count();
    }

    /**
     * 게시글 4개 반환 (메인페이지용)
     */
    public List<RecruitBoardMainDto> getMainList() {
        Pageable pageable = PageRequest.of(0, 4, Sort.Direction.DESC, "createdAt");
        return recruitBoardRepository.findAll(pageable).getContent()
                .stream()
                .map(RecruitBoardMainDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 모집 완료
     */
    @Transactional
    public void complete(Long boardId, Long userId) {
        RecruitBoard recruitBoard = recruitBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("모집 완료 처리 실패, 해당 게시글이 없습니다."));

        if (recruitBoard.getUser().getId().equals(userId) && !recruitBoard.isCompleted()) {
            recruitBoard.complete();
        }
    }

    /**
     * 신청자 목록
     */
    public List<ApplicantDto> getApplicants(Long boardId, Long userId) {
        RecruitBoard recruitBoard = recruitBoardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("신청자 목록 조회 실패, 해당 게시글이 없습니다."));
        List<Party> parties = partyRepository.findByRecruitBoardId(boardId);

        if (recruitBoard.getUser().getId().equals(userId)) {
            return parties.stream().map(party -> {
                ApplicantDto dto = new ApplicantDto(party.getUser());
                dto.setMeetRequired(party.isMeetRequired());
                if (party.getIsMeetOptional() == null) {
                    dto.setIsMeetOptional(null);
                } else {
                    dto.setIsMeetOptional(party.getIsMeetOptional());
                }
                dto.setApproved(party.isApproved());

                return dto;
            }).collect(Collectors.toList());
        } else {
            throw new IllegalArgumentException("신청자 목록 조회 실패, 해당 게시글을 작성한 유저가 아닙니다.");
        }
    }

    /**
     * 신청자 수
     */
    public Long getApplicantsNumber(Long boardId) {
        return partyRepository.countByRecruitBoardId(boardId);
    }

    /**
     * 승인된 인원 수
     */
    public Long getApproversNumber(Long boardId) {
        return partyRepository.countByRecruitBoardIdAndIsApprovedTrue(boardId);
    }

    /**
     * 신청 여부 확인
     */
    public Boolean applicationCheck(Long boardId, Long userId) {
        Optional<Party> result = partyRepository.findByUserIdAndRecruitBoardId(userId, boardId);

        if (result.isEmpty()) return null;
        else return result.get().isApproved();
    }

    /**
     * 이미지 추출
     */
    private List<ImageDto> extractImagesFromContent(String content) {
        List<ImageDto> images = new ArrayList<>();

        // 정규표현식 패턴
        String patternString = "<img\\s+[^>]*src\\s*=\\s*\"([^\"]*)\"[^>]*>";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(content);

        // 매칭된 이미지 URL 추출
        while (matcher.find()) {
            String imageUrl = matcher.group(1);
            ImageDto imageDto = new ImageDto();
            imageDto.setImageUrl(imageUrl);
            images.add(imageDto);
        }

        return images;
    }


}
