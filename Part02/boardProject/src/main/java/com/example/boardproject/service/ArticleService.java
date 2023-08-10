package com.example.boardproject.service;

import com.example.boardproject.domain.type.SearchType;
import com.example.boardproject.dto.ArticleDto;
import com.example.boardproject.dto.ArticleWithCommentsDto;
import com.example.boardproject.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
* @Transactional
* 클래스 레벨에서 @Transactional을 걸어주면, 클래스 내의 전체 매소드를 하나의 트랜잭션 취급한다.
* 이 뜻은, 클래스 내부의 여러 매소드에 걸쳐 하나의 트랙잭션으로 사용한다는 의미로, 하나의 매소드에서 에러가 발생하면 다른 매소드에 영향을 미칠 수 있다.
* 반대로, 매소드 레벨에서 @Transactional을 걸어주면 메소드끼리 영향을 받지 않는다.
* */
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;
    /*
    * Page 클래스
    * 데이터의 페이지네이션을 처리하기 위한 클래스
    * 주로 데이터를 페이지 단위로 나누어 표시하거나 조회할 때 사용
    * */
    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        return Page.empty();
    }
    @Transactional(readOnly = true)
    public ArticleDto searchArticle(long l) {
        return null;
    }
    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) {
        return null;
    }
    public void saveArticle(ArticleDto dto) {
    }

    public void updateArticle(ArticleDto dto) {
    }

    public void deleteArticle(Long articleId) {
    }

}
