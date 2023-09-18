package com.zuitt.wdc044.models;

import javax.persistence.*;

//mark this Java Object as a representation of a database table via @Entity annotation
@Entity

@Table(name = "posts")
public class Post {
    //indicate that this property represents the primary key;
    @Id
    // values for this property will be auto-incremented
    @GeneratedValue
    private Long id;

    // class properties that represent table columns in a relational database and is annotated by Column
    @Column
    private String title;

    @Column
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public User getUser(){
        return this.user;
    }

    public void setUser(User user){
        this.user = user;
    }

    //Constructors;
    public Post(){}

    public Post(String title, String content){
        this.title = title;
        this.content = content;
    }

    public Post(String title, String content, User user){
        this.title = title;
        this.content = content;
        this.user =user;
    }

    //getters and setters:
    public String getTitle(){
        return this.title;
    }

    public String getContent(){
        return this.content;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setContent(String content){
        this.content = content;
    }
}
