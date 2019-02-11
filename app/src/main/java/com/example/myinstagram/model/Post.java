package com.example.myinstagram.model;

import lombok.Data;

@Data
public class Post {

    private User user;
    private String imageURL;
    private int likes;
    private String description;

    public Post(String userName, String imageURL, String description) {
        User newUser = new User();
        newUser.setName(userName);
        this.user = newUser;
        this.imageURL = imageURL;
        this.description = description;
    }
}
