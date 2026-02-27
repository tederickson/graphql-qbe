package com.erickson.graphql_db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@Data
public final class CommentEntity {
    @Id
    @GeneratedValue
    private Long commentId;

    private String name;
    private String content;
    private LocalDateTime publishedOn;
    private LocalDateTime updatedOn;

    @ManyToOne
    @JoinColumn(name = "bookId", referencedColumnName = "bookId", nullable = false)
    private BookEntity book;
}
