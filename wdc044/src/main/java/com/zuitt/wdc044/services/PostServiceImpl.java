package com.zuitt.wdc044.services;

import com.zuitt.wdc044.models.Post;
import com.zuitt.wdc044.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.zuitt.wdc044.config.JwtToken;


import com.zuitt.wdc044.models.User;
import com.zuitt.wdc044.repositories.UserRepository;

import java.util.Optional;
import java.util.List;



@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    JwtToken jwtToken;

    public void createPost(String stringToken, Post post){
        String username = jwtToken.getUsernameFromToken(stringToken);
        User author = userRepository.findByUsername(username);

        Post newPost = new Post(post.getTitle(), post.getContent(), author);
        postRepository.save(newPost);
    }

    public Iterable<Post> getPosts(){
        //this method is from the CrudRepository wherein it will contain or get all the records inside its table.
        return postRepository.findAll();
    }

    public ResponseEntity<?> updatePost(Long id, String stringToken, Post post){
        //We try to capture the username of the author who posted this specific post
        Post postForUpdating = postRepository.findById(id).get();
        String postAuthor = postForUpdating.getUser().getUsername();

        //We are going to get the username of the user who owns the provided Token
        String authenticatedUser = jwtToken.getUsernameFromToken(stringToken);

        if(authenticatedUser.equals(postAuthor)){
            postForUpdating.setTitle(post.getTitle());
            postForUpdating.setContent(post.getContent());

            //save to the database
            postRepository.save(postForUpdating);

            return new ResponseEntity<>("Post updated successfully", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("You are not authorized to edit this post", HttpStatus.UNAUTHORIZED);
        }

    }


    //implementation of the deletePost action from the PostService:
    public ResponseEntity<?> deletePost(Long id, String stringToken){

        Optional<Post> postForDeleting2 = postRepository.findById(id);

        if(postForDeleting2.isEmpty()){
            return new ResponseEntity<>("Post cannot be found", HttpStatus.BAD_REQUEST);
        }else{

            Post postForDeleting = postRepository.findById(id).get();
            String postAuthor = postForDeleting.getUser().getUsername();

            String authenticatedUser = jwtToken.getUsernameFromToken(stringToken);
            if(authenticatedUser.equals(postAuthor)){

                postRepository.deleteById(id);

                return new ResponseEntity<>("Post deleted successfully!", HttpStatus.OK);
            }else{
                return new ResponseEntity<>("You are not authorized to delete this post.", HttpStatus.UNAUTHORIZED);
            }
        }




    }

    public List<Post> getPostsByUser(String stringToken) {
        String username = jwtToken.getUsernameFromToken(stringToken);
        User user = userRepository.findByUsername(username);
        return postRepository.findByUser(user);
    }

}
