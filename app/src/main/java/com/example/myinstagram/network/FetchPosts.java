package com.example.myinstagram.network;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.myinstagram.model.GetPost;
import com.example.myinstagram.model.Post;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class FetchPosts extends AsyncTask<Void, Void, Void> {

    private static final String TAG = FetchPosts.class.getName();
    private static final String POST_URL =  "https://jsonblob.com/api/jsonblob/4074c5dc-2dd1-11e9-8c29-6d3427129fcf";
    private static final int POSTS_TO_FETCH = 10;
    private List<Post> postsData;
    private AysncTaskCallBack homeFragmentCallBack;

    public FetchPosts(Fragment fragment) {
        this.postsData = new ArrayList<>();
        this.homeFragmentCallBack = (AysncTaskCallBack) fragment;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        return getPostsSync();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        this.homeFragmentCallBack.updatePosts(this.postsData);
    }

    private Void getPostsSync() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(POST_URL).build();

        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);

            ResponseBody responseBody = response.body();
            String apiResponse = null;
            if(responseBody != null)
                apiResponse = responseBody.string();
            Gson gson = new Gson();
            GetPost getPost = gson.fromJson(apiResponse, GetPost.class);
            Post[] allPosts = getPost.getPosts();

            initializePostData(allPosts);
        } catch (IOException e) {
            Log.d(TAG, e.getMessage());
        }
        return null;
    }

    private void initializePostData(Post[] allPosts) {
        int i=0;
        for(Post post: allPosts) {
            this.postsData.add(new Post(post));
            if(++i == POSTS_TO_FETCH)
                break;
        }
    }
}
