package com.erickson.graphql_db.repository;

import com.erickson.graphql_db.model.AuthorEntity;
import com.erickson.graphql_db.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long>, QueryByExampleExecutor<PostEntity> {
    List<PostEntity> findAllByAuthor(AuthorEntity author);
}
