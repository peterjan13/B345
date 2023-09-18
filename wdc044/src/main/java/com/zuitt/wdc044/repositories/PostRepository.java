package com.zuitt.wdc044.repositories;

import com.zuitt.wdc044.models.Post;
import com.zuitt.wdc044.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Long> {
    List<Post> findByUser(User user);
}
