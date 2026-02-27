package com.erickson.graphql_db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "author")
@Data
@Builder
@AllArgsConstructor  // For Builder
@NoArgsConstructor   // For JPA
public final class AuthorEntity {
    @Id
    @GeneratedValue
    Long authorId;

    // Camel case resolves to column "first_name"
    String firstName;
    String lastName;
    String email;

    // Resolves to column "username"
    String username;

    // Only fetch all related Books if GraphQl needs the information
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    List<BookEntity> books;
}
