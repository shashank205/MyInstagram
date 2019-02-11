package com.example.myinstagram.model;

import lombok.Data;

@Data
public class Story {
    private User user;
    private String imageURL;

    public Story(String userName, String imageURL) {
        User newUser = new User();
        newUser.setName(userName);
        this.user = newUser;
        this.imageURL = imageURL;
    }
}
