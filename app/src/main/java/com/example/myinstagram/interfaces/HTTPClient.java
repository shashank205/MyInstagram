package com.example.myinstagram.interfaces;

import android.support.v4.app.Fragment;

public interface HTTPClient {
    void makeHTTPGetRequest(String url, Fragment fragment);
}
