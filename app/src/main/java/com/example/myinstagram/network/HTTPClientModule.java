package com.example.myinstagram.network;

import com.example.myinstagram.interfaces.HTTPClient;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class HTTPClientModule {
    @Provides
    @Named("OkHTTP")
    HTTPClient provideOkHTTPUtil() {
        return new OkHTTPUtil();
    }
}
