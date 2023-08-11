package com.example.boardproject.repository.querydsl;

import java.util.List;

public interface ArticleRepositoryCustom {
    // JPA는 도메인 단위로만 반환 가능 -> String과 같은 형식으로 반환 불가능
    List<String> findAllDistinctHashtags();
}
