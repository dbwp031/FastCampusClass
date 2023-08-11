# ReferenceId

/*        // id가 있음을 이미 알고 있다.
// 엔티티를 영속성 컨텍스트에서 가져와야 해서 findById쿼리를 사용
// getReferenceById를 사용해서 레퍼런스만 가져올 수 있다.
Article article = articleRepository.findById(dto.id());
article.setHashtag("#java");
articleRepository.save(article);*/

```java
    public void updateArticle(ArticleDto dto) {
        // id가 있음을 이미 알고 있다.
        // 엔티티를 영속성 컨텍스트에서 가져와야 해서 findById쿼리를 사용
        // getReferenceById를 사용해서 레퍼런스만 가져올 수 있다.
        Article article = articleRepository.findById(dto.id());
        article.setHashtag("#java");
        articleRepository.save(article);
    }
```

```java
    public void updateArticle(ArticleDto dto) {
        Article article = articleRepository.getReferenceById(dto.id());
        if(dto.title() != null) {article.setTitle(dto.title());}
        if(dto.content()!= null) { article.setContent(dto.content());}
        article.setHashtag(dto.hashtag());
        // save 필요없음. -> 트랜잭션이 끝날 때 영속성 컨텍스트는 아티클이 변한 것을 감지 및 쿼리 날림.
    }
```