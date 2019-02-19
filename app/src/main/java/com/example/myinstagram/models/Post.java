package com.example.myinstagram.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Post extends RealmObject {
    @PrimaryKey
    private Long id;
    private String caption;
    private String location;
    private int likes;
    private int comments;
    private String imageUrl;
    private boolean likeStatus;
    private User user;

    public Post() {
        //No arguments
    }
    public Post(Post post) {
        this.id = post.getId();
        this.caption = post.getCaption();
        this.location = post.getLocation();
        this.likes = post.getLikes();
        this.comments = post.getComments();
        this.imageUrl = post.getImageUrl();
        this.likeStatus = post.isLikeStatus();
        this.user = post.getUser();
    }
}
