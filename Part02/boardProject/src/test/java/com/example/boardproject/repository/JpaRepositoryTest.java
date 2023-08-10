package com.example.boardproject.repository;

import com.example.boardproject.config.JpaConfig;
import com.example.boardproject.domain.Article;
import com.example.boardproject.domain.ArticleComment;
import com.example.boardproject.domain.UserAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JPA 연결 테스트")
// application.yaml에서 h2를 여러 호환모드로 사용할 수 있음.
// 하지만 @DataJpaTest를 사용하는 순간 자기가 지정한 defualt 모드로 동작함
// 이를 막기위한 어노테이션이 아래 어노테이션임
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(JpaConfig.class) // JpaConfig의 JpaAuditing을 인식할 수 있도록 함.
@DataJpaTest // method 단위로 자동으로 트랜잭션이 걸려있음
class JpaRepositoryTest {

    private final ArticleRepository articleRepository; // junit5부터 autowired 기능이 포함되어서 생성자 주입 패턴으로 생성
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository,
            @Autowired UserAccountRepository userAccountRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
    }
    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // Given

        // When
        List<Article> articles = articleRepository.findAll();
        List<ArticleComment> articleComments = articleCommentRepository.findAll();
        // Then
        assertThat(articles)
                .isNotNull()
                .hasSize(100);
        assertThat(articleComments)
                .isNotNull()
                .hasSize(1000);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        // Given
        long previousCount = articleRepository.count();
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("uno", "pw", null, null, null));
        Article article = Article.of(userAccount, "new article", "new content", "#Spring");
        // When
//        List<Article> articles = articleRepository.findAll();
//        List<ArticleComment> articleComments = articleCommentRepository.findAll();
        articleRepository.save(article);
        // Then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }
    // update query 발생하지 않음 -> 트랙잭션(롤백)때문에
    //
    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#Springboot";
        article.setHashtag(updatedHashtag);
        // When
        Article savedArticle = articleRepository.saveAndFlush(article);
        // save하면 업데이트 안됨 -> saveAndFlush해서 업데이트 반영하도록 함 -> 이 메소드가 끝난 후 롤백됨.
        // Then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);

    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentSize = article.getArticleComments().size();
        // When
        articleRepository.delete(article);
        // Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentSize);

    }

    // 테스트 데이터를 대량으로 자동 생성해주는 서비스: mockaroo
    // https://www.mockaroo.com/schemas/new
}