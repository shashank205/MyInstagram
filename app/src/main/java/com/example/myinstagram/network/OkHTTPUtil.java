package com.example.myinstagram.network;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.example.myinstagram.interfaces.HttpCallBack;
import com.example.myinstagram.interfaces.HTTPClient;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHTTPUtil implements HTTPClient {

    private final OkHttpClient client = new OkHttpClient();

    static OkHTTPUtil newInstance() {
        return new OkHTTPUtil();
    }

    @Override
    public void makeHTTPGetRequest(String url, Fragment fragment) {

        HttpCallBack httpCallBack = (HttpCallBack) fragment;

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                httpCallBack.onFailure(null, e, "Failed to make network call : ");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                ResponseBody responseBody = response.body();
                if (!response.isSuccessful()) {
                    httpCallBack.onFailure(response, null, "Response not Successful : ");
                }
                httpCallBack.onSuccess(responseBody);
            }
        });
    }
}