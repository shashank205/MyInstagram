package com.example.myinstagram.models;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends RealmObject {
    @PrimaryKey
    private Long id;
    private String name;
    private String avatarUrl;
}
