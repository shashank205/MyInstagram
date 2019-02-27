package com.example.myinstagram.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myinstagram.R;
import com.example.myinstagram.adapters.PostsAdapter;
import com.example.myinstagram.adapters.StoriesAdapter;
import com.example.myinstagram.databinding.FragmentHomeBinding;
import com.example.myinstagram.databinding.StoryRecylerCardBinding;
import com.example.myinstagram.interfaces.HTTPClient;
import com.example.myinstagram.interfaces.HttpCallBack;
import com.example.myinstagram.models.Post;
import com.example.myinstagram.models.Story;
import com.example.myinstagram.storage.SharedPreferencesStorage;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getName();
    private static final String TIME_STAMP_KEY = "timestamp";
    private List<Post> postsData;
    private List<Story> storiesData;
    private PostsAdapter postsAdapter;
    private StoriesAdapter storiesAdapter;
    private FragmentHomeBinding fragmentHomeBinding;
    private Context context;
    @Inject
    HTTPClient httpClient;
    @Inject
    SharedPreferencesStorage sharedPreferencesStorage;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidSupportInjection.inject(this);
        super.onCreate(savedInstanceState);
        this.postsData = new ArrayList<>();
        this.storiesData = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.fragmentHomeBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_home, container, false);
        return this.fragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.fragmentHomeBinding.homePostRecyclerView.setLayoutManager(new LinearLayoutManager(this.context));
        this.postsAdapter = new PostsAdapter(this.postsData, this);
        this.fragmentHomeBinding.homePostRecyclerView.setAdapter(this.postsAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL);
        this.fragmentHomeBinding.homePostRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        long savedTimestamp = this.sharedPreferencesStorage.readValue(TIME_STAMP_KEY, (long)0);
        Date date = new Date();
        if( date.getTime() >= savedTimestamp ) {
            Log.d(TAG, "Timestamp expired");
            fetchPostsFromServer();
        } else {
            Log.d(TAG, "Timestamp valid");
            fetchPostsFromDatabase();
        }
    }

    private void fetchPostsFromServer() {
        final String POSTS_GET_URL = "https://jsonblob.com/api/jsonblob/4074c5dc-2dd1-11e9-8c29-6d3427129fcf";
        ConnectivityManager connectivityManager = null;
        NetworkInfo networkInfo = null;
        if(getActivity() != null)
            connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            httpClient.makeHTTPGetRequest(POSTS_GET_URL, httpCallBack);
        }
    }

    private void fetchPostsFromDatabase() {
        this.postsData.clear();
        Realm realmDefaultInstance = Realm.getDefaultInstance();
        RealmResults <Post> realmPosts = realmDefaultInstance.where(Post.class).findAll();
        List<Post> unManagedPosts = realmDefaultInstance.copyFromRealm(realmPosts);
        this.postsData.addAll(unManagedPosts);
        realmDefaultInstance.close();
        this.postsAdapter.notifyDataSetChanged();
    }


    HttpCallBack httpCallBack = new HttpCallBack() {
        @Override
        public void onFailure(Response response, Throwable throwable, String message) {
            if(throwable != null) {
                Log.e(TAG, message, throwable);
            }
        }

        @Override
        public void onSuccess(ResponseBody responseBody) {
            try {
                String apiResponse = responseBody.string();
                Gson gson = new Gson();
                PostJSONMapper postJSONMapper = gson.fromJson(apiResponse, PostJSONMapper.class);
                savePostsInDatabase(postJSONMapper.getPosts());
                sharedPreferencesStorage.writeValue(TIME_STAMP_KEY, postJSONMapper.getTimestamp());
            } catch (IOException e) {
                Log.e(TAG, "ResponseBody to String conversion failed : ", e);
            }
        }

        private void savePostsInDatabase(List<Post> postsToSave) {
            Realm realmDefaultInstance = Realm.getDefaultInstance();
            realmDefaultInstance.beginTransaction();
            RealmList<Post> postRealmList = new RealmList<>();
            int count = context.getResources().getInteger(R.integer.posts_to_display);
            int i = 0;
            for(Post post: postsToSave) {
                postsData.add(post);
                if(++i == count)
                    break;
            }
            postRealmList.addAll(postsData);
            realmDefaultInstance.insertOrUpdate(postRealmList);
            realmDefaultInstance.commitTransaction();
            realmDefaultInstance.close();

            if(getActivity() != null) {
                getActivity().runOnUiThread(() -> postsAdapter.notifyDataSetChanged());
            }
        }
    };

    public void createStoryRecyclerInPostRecycler(StoryRecylerCardBinding storyRecylerCardBinding) {
        storyRecylerCardBinding.storyRecyclerCard.setLayoutManager(
                new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false));
        this.storiesAdapter = new StoriesAdapter(this.context, this.storiesData);
        storyRecylerCardBinding.storyRecyclerCard.setAdapter(this.storiesAdapter);
        initializeStoryData();
    }

    private void initializeStoryData() {
        this.storiesData.clear();
        String[] userName = this.context.getResources().getStringArray(R.array.story_user_name);
        String[] imageURLs = this.context.getResources().getStringArray(R.array.story_image_url);

        for (int i = 0; i < userName.length; i++) {
            this.storiesData.add(new Story(userName[i], imageURLs[i]));
        }
        this.storiesAdapter.notifyDataSetChanged();
    }
}