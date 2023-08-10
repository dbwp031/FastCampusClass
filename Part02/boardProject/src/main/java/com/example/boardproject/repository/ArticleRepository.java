package com.example.boardproject.repository;

import com.example.boardproject.domain.Article;
import com.example.boardproject.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>, // 기본적인 검색 기능
        QuerydslBinderCustomizer<QArticle> //추가적인 customizing 검색 기능 (BinderCustomizer는 QClass를 넣어야 함.)
{
    Page<Article> findByTitle(String title, Pageable pageable);
    @Override // 이 안의 내용으로 검색에 대한 세부적인 규칙이 변경된다.
    default void customize(QuerydslBindings bindings, QArticle root){
        bindings.excludeUnlistedProperties(true); // 현재 QuerydslPredicateExecutor로 인해서 검색에 대하여 모든 필드가 열려있다. -> 필드를 선택해서 해당 필드만 검색할 수 있도록 하고 싶다.
        bindings.including(root.title, root.content, root.hashtag, root.createdAt, root.createdBy);
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // like '${v}'
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // like '%${v}%'
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);

    }

}
