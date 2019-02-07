package com.example.myinstagram.model;

import lombok.Data;

@Data
public class Story {
    private User user;
    private int imageResource;

    public Story(String userName, int imageResource) {
        User newUser = new User();
        newUser.setName(userName);
        this.user = newUser;
        this.imageResource = imageResource;
    }
}
