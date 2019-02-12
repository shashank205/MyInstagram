package com.example.myinstagram.model;

import lombok.Data;

@Data
public class GetPost {
    private double timestamp;
    private Post[] posts;
}
