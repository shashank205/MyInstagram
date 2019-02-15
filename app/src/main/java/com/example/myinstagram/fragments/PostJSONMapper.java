package com.example.myinstagram.fragments;

import com.example.myinstagram.models.Post;

import lombok.Data;

@Data
class PostJSONMapper {
    private double timestamp;
    private Post[] posts;
}
