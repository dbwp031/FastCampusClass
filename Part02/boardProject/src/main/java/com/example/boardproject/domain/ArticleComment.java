package com.example.boardproject.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.Objects;

@Getter
@ToString(callSuper = true)
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@Entity
public class ArticleComment extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @ManyToOne(optional = false) private Article article; // 게시글 (ID)
    @Setter @ManyToOne(optional = false) @JoinColumn(name = "userId") private UserAccount userAccount;

    @Setter @Column(nullable = false, length = 500) private String content; // 본문

    protected ArticleComment() {
    }

    private ArticleComment(Article article,UserAccount userAccount, String content) {
        this.article = article;
        this.content = content;
        this.userAccount = userAccount;
    }

    public static ArticleComment of(Article article,UserAccount userAccount, String content) {return new ArticleComment(article,userAccount, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticleComment that = (ArticleComment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
