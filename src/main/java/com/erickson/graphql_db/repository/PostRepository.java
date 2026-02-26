package com.erickson.graphql_db.repository;

import com.erickson.graphql_db.model.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long>, QueryByExampleExecutor<PostEntity> {
}
