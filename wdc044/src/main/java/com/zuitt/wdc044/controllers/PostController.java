package com.zuitt.wdc044.controllers;

import com.zuitt.wdc044.models.Post;
import com.zuitt.wdc044.services.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class PostController {
    @Autowired
    PostServiceImpl postServiceImpl;
    //route for creating a new post
    @RequestMapping(value = "/posts", method = RequestMethod.POST)
    public ResponseEntity<?> createPost(@RequestHeader(value = "Authorization")String stringToken, @RequestBody Post post){
        postServiceImpl.createPost(stringToken, post);

        return new ResponseEntity<>("Post created successfully", HttpStatus.CREATED);
    }
    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public ResponseEntity<?> getPosts(){
        return new ResponseEntity<>(postServiceImpl.getPosts(), HttpStatus.OK);
    }

    //This route is for editing a post
    @RequestMapping(value = "/posts/{postid}", method = RequestMethod.PUT)
    public ResponseEntity<?> updatePost(@PathVariable Long postid, @RequestHeader(value = "Authorization") String stringToken, @RequestBody Post post){
        return postServiceImpl.updatePost(postid, stringToken, post);
    }

    //This route is for the deletion of specific post:
    @RequestMapping(value = "/posts/{postid}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePost(@PathVariable Long postid, @RequestHeader(value = "Authorization") String stringToken){
        return postServiceImpl.deletePost(postid, stringToken);
    }

    @RequestMapping(value = "/myposts", method = RequestMethod.GET)
    public ResponseEntity<?> getMyPosts(@RequestHeader(value = "Authorization") String stringToken){
        return new ResponseEntity<>(postServiceImpl.getPostsByUser(stringToken), HttpStatus.OK);
    }

}
