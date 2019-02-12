package com.example.myinstagram.network;

import com.example.myinstagram.interfaces.HTTPClient;

public class HTTPClientFactory {

    private HTTPClientFactory() {
        //Do nothing
    }

    public static HTTPClient getOKHTTPUtil() {
        return OkHTTPUtil.newInstance();
    }
}
