package com.example.myinstagram.model;

import lombok.Data;

@Data
public class Post {

    private User user;
    private int contentResource;
    private int likes;
    private String description;

    public Post(String userName, int contentResource, String description) {
        User newUser = new User();
        newUser.setName(userName);
        this.user = newUser;
        this.contentResource = contentResource;
        this.description = description;
    }
}
