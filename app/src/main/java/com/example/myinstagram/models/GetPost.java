package com.example.myinstagram.models;

import lombok.Data;

@Data
public class GetPost {
    private double timestamp;
    private Post[] posts;
}
