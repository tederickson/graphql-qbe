package com.erickson.graphql_db.repository;

import com.erickson.graphql_db.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.graphql.data.GraphQlRepository;

@GraphQlRepository
public interface CommentRepository extends JpaRepository<CommentEntity, Long>, QueryByExampleExecutor<CommentEntity> {
}
