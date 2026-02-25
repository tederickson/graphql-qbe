package com.erickson.graphql_db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "post")
@Data
public class PostEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long authorId;

    private String title;
    private String content;
    private LocalDateTime publishedOn;
    private LocalDateTime updatedOn;
}
