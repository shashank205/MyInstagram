package com.example.myinstagram.fragments;

import com.example.myinstagram.models.Post;

import java.util.List;

import lombok.Data;

@Data
class PostJSONMapper {
    private Long timestamp;
    private List<Post> posts;
}
