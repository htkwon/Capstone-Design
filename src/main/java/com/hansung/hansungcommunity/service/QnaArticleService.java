package com.hansung.hansungcommunity.service;


import com.hansung.hansungcommunity.dto.QnaArticleDto;
import com.hansung.hansungcommunity.dto.QnaArticleResponseDto;
import com.hansung.hansungcommunity.entity.QnaArticle;
import com.hansung.hansungcommunity.entity.User;
import com.hansung.hansungcommunity.repository.QnaArticleRepository;
import com.hansung.hansungcommunity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class QnaArticleService {
    private final UserRepository userRepository;

    private final QnaArticleRepository qnaArticleRepository;


    @Transactional(readOnly = true)
    public QnaArticleDto getArticle(Long articleId) {
        return qnaArticleRepository.findById(articleId)
                .map(QnaArticleDto::from)
                .orElseThrow(()-> new EntityNotFoundException(articleId+" 인 게시글이 없습니다."));
    }


    public QnaArticleDto saveArticle(QnaArticleDto dto, Long userId) {
        QnaArticle article = dto.toEntity();

        User user = userRepository.getReferenceById(userId);
        article.setUser(user);

        QnaArticle qna = qnaArticleRepository.save(article);
        QnaArticleDto res = QnaArticleDto.from(qna);
        return res;
    }

    public void updateArticle(QnaArticleDto dto) {
        QnaArticle target = qnaArticleRepository.getReferenceById(dto.getId());
        target.updateArticle(dto);
        qnaArticleRepository.save(target);
        /*
         getReferenceById 메소드를 사용하여,
         내부의 값을 필요로 하지는 않고, 다른 객체에게 할당하는 목적으로만 조회하는 경우
         성능상 이점이 있을 수 있음
         */
    }

    public void deleteArticle(long articleId) {
        qnaArticleRepository.deleteById(articleId);

    }

    //홈화면 - qna 게시글 조회(4개)
    public List<QnaArticleResponseDto> findAll() {
        Pageable pageable = PageRequest.of(0,4, Sort.Direction.DESC,"createdAt");
        return qnaArticleRepository.findAll(pageable).getContent()
                .stream()
                .map(QnaArticleResponseDto::new)
                .collect(Collectors.toList());
    }
    /**
        limit을 사용하면 full scan한 후 메모리에 로드한 후 limit하여
        의도치않게 모두 메모리에 올린다는 문제가 있다고해서
        paging을 사용하여 query자체에서 limit하게 하였음
     **/

}
