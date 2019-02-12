package com.example.myinstagram.network;

import com.example.myinstagram.model.Post;

import java.util.List;

public interface AysncTaskCallBack {
    void updatePosts(List<Post> posts);
}
