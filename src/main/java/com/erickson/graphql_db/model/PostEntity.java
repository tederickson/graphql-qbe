package com.erickson.graphql_db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "post")
@Data
@ToString(exclude = "author")
public class PostEntity {
    @Id
    @GeneratedValue
    private Long postId;

    private String title;
    private String content;
    private LocalDate publishedOn;

    @ManyToOne
    @JoinColumn(name = "authorId", referencedColumnName = "authorId", nullable = false)
    private AuthorEntity author;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    List<CommentEntity> comments;
}
