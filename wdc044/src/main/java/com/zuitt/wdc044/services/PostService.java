package com.zuitt.wdc044.services;

import com.zuitt.wdc044.models.Post;
import org.springframework.http.ResponseEntity;

public interface PostService {
    void createPost(String stringToken, Post post);
    Iterable<Post> getPosts();

    ResponseEntity<?> updatePost(Long id, String stringToken, Post post);

    ResponseEntity<?> deletePost(Long id, String stringToken);
}
