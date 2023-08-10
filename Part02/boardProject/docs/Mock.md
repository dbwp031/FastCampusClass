# Mock, Mockito, BDDMockito

## Mock (모의 객체)
* 진짜 객체와 비슷하게 동작하지만, 프로그래머가 직접 행동을 관리하는 객체
* 실제 시스템의 일부분 (ex: 데이터베이스, 네트워크 연결 등)을 데체하여 테스트의 속도를 높이거나, 특정한 조건을 제어하기 위해 사용된다.
* Mock 객체를 사용하면 외부 시스템과의 의존성 없이 특정 로직 또는 기능을 테스트할 수 있다.
* 실제 객체를 만들기에는 비용과 시간, 의존성이 크게 걸쳐져 있어 테스트 시 제대로 구현하기 어려울 경우 만든다.

## Mockito (Java 오픈 소스 테스트 프레임워크)
Mock 객체를 쉽게 만들고, 관리하고, 검증할 수 있는 방법을 제공하는 `Java 오픈 소스 테스트 프레임워크`

## BDDMockito (Behavior-Driven-Development)
Behavior Driven Development (BDD)는 소프트웨어 개발의 접근법 중 하나로, 사용자의 행동을 중심으로 개발을 진행하는 방식  
BDDMockito는 Mockito의 BDD 스타일 API를 제공한다. (`given, when, then`)  
BDDMockito를 사용하면 테스트 코드를 더 읽기 쉬운 형태로 작성할 수 있으며, 테스트의 의도와 결과를 명확하게 파악할 수 있습니다.
* given: 시스템의 특정한 상태나 초기 조건을 설정하는 부분. 주로 Mock 객체의 동작을 정의
* when: 테스트하려는 실제 행동을 수행하는 부분.
* then: 행동의 결과를 검증하는 부분. 

### 사용 예시
* [Given] `given(articleCommentRepository.findByArticle_Id(articleId)).willReturn(List.of(expected));`
  *  `articleCommentRepository.findByArticle_Id(articleId)` 메서드 실행 시 `expected 리스트`를 반환하도록 설정
  * `articleCommentRepository`가 Mock 객체
* [When] `List<ArticleCommentDto> actual = sut.searchArticleComments(articleId);`
  * ```java
    @Transactional(readOnly = true)
    public List<ArticleCommentDto> searchArticleComments(Long articleId) {
        return articleCommentRepository.findByArticle_Id(articleId) // <- 여기서 given이 사용됨.
                .stream()
                .map(ArticleCommentDto::from)
                .toList();
    }
* [Then] `then(articleCommentRepository).should().findByArticle_Id(articleId);` 
  * `findByArticle_Id(articleId)`가 호출되었는지 검증한다.
```java
    @DisplayName("게시글 ID로 조회하면, 해당하는 댓글 리스트를 반환한다.")
    @Test
    void givenArticleId_whenSearchingComments_thenReturnsComments() {
        // Given
        Long articleId = 1L;
        ArticleComment expected = createArticleComment("content");
        given(articleCommentRepository.findByArticle_Id(articleId)).willReturn(List.of(expected));
            
        // When
        List<ArticleCommentDto> actual = sut.searchArticleComments(articleId);
        
        // Then
        assertThat(actual)
                .hasSize(1)
                .first().hasFieldOrPropertyWithValue("content", expected.getContent());
        then(articleCommentRepository).should().findByArticle_Id(articleId);
    }
```
