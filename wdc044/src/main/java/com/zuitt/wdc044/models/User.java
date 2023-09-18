package com.zuitt.wdc044.models;



import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    public Set<Post> posts;

    public User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Set<Post> getPosts() {
        return this.posts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId(){
        return id;
    }
}

