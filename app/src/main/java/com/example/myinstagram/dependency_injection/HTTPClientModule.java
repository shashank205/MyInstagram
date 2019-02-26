package com.example.myinstagram.dependency_injection;

import com.example.myinstagram.interfaces.HTTPClient;
import com.example.myinstagram.network.OkHTTPUtil;

import dagger.Module;
import dagger.Provides;

@Module
class HTTPClientModule {

    @Provides
    HTTPClient getOkHTTP() {
        return new OkHTTPUtil();
    }
}
