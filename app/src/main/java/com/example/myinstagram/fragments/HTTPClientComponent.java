package com.example.myinstagram.fragments;

import com.example.myinstagram.interfaces.HTTPClient;
import com.example.myinstagram.network.HTTPClientModule;

import javax.inject.Named;

import dagger.Component;

@Component(modules = {HTTPClientModule.class})
public interface HTTPClientComponent {
    @Named("OkHTTP")
    HTTPClient getOkHTTP();
}
