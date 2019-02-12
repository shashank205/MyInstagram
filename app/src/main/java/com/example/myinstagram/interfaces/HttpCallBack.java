package com.example.myinstagram.interfaces;

import okhttp3.Response;
import okhttp3.ResponseBody;

public interface HttpCallBack {

    void onFailure(Response response, Throwable throwable, String message);
    void onSuccess(ResponseBody responseBody);
}
