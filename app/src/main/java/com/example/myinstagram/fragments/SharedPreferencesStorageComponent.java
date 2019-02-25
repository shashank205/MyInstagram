package com.example.myinstagram.fragments;

import com.example.myinstagram.storage.SharedPreferencesStorage;
import com.example.myinstagram.storage.SharedPreferencesStorageModule;

import dagger.Component;

@Component(modules = SharedPreferencesStorageModule.class)
public interface SharedPreferencesStorageComponent {
    SharedPreferencesStorage getSharedPreferencesStorage();
}
