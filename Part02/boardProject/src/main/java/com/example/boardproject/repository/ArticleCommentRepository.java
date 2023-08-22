package com.example.boardproject.repository;

import com.example.boardproject.domain.ArticleComment;
import com.example.boardproject.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface ArticleCommentRepository extends
        JpaRepository<ArticleComment, Long>,
        QuerydslPredicateExecutor<ArticleComment>,
        QuerydslBinderCustomizer<QArticleComment>
{
    List<ArticleComment> findByArticle_Id(Long articleId);

    void deleteByIdAndUserAccount_UserId(Long articleCommentId, String userId);
    @Override // 이 안의 내용으로 검색에 대한 세부적인 규칙이 변경된다.
    default void customize(QuerydslBindings bindings, QArticleComment root){
        bindings.excludeUnlistedProperties(true); // 현재 QuerydslPredicateExecutor로 인해서 검색에 대하여 모든 필드가 열려있다. -> 필드를 선택해서 해당 필드만 검색할 수 있도록 하고 싶다.
        bindings.including(root.content, root.createdAt, root.createdBy);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
