package com.example.boardproject.repository.querydsl;

import com.example.boardproject.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface ArticleRepositoryCustom {
    // JPA는 도메인 단위로만 반환 가능 -> String과 같은 형식으로 반환 불가능

    @Deprecated
    List<String> findAllDistinctHashtags();

    Page<Article> findByHashtagNames(Collection<String> hashtagNames, Pageable pageable);
}
