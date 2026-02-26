package com.erickson.graphql_db.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "author")
@Data
@Builder
@AllArgsConstructor  // For Builder
@NoArgsConstructor   // For JPA
public final class AuthorEntity {
    @Id
    @GeneratedValue
    Long id;

    // Camel case resolves to column "first_name"
    String firstName;
    String lastName;
    String email;

    // Resolves to column "username"
    String username;
}
