package com.hansung.hansungcommunity.service;


import com.hansung.hansungcommunity.dto.QnaArticleDto;
import com.hansung.hansungcommunity.entity.QnaArticle;
import com.hansung.hansungcommunity.repository.QnaArticleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class QnaArticleService {

    private final QnaArticleRepository qnaArticleRepository;

    @Transactional(readOnly = true)
    public QnaArticleDto getArticle(Long articleId) {
        return qnaArticleRepository.findById(articleId)
                .map(QnaArticleDto::from)
                .orElseThrow(()-> new EntityNotFoundException(articleId+" 인 게시글이 없습니다."));
    }


    public void saveArticle(QnaArticleDto dto) {
        QnaArticle article = dto.toEntity();
        qnaArticleRepository.save(article);

    }

    public void updateArticle(QnaArticleDto dto) {
        QnaArticle article = qnaArticleRepository.getReferenceById(dto.getId());
        if(dto.getTitle()!=null){article.setTitle(dto.getTitle());}
        if(dto.getContent()!=null){article.setContent(dto.getContent());}
        if(dto.getContent()!=null){article.setContent(dto.getContent());}
        article.setTag(dto.getTag());
    }

    public void deleteArticle(long articleId) {
        qnaArticleRepository.deleteById(articleId);

    }
}
