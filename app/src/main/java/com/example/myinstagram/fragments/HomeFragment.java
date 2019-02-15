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
import com.example.myinstagram.databinding.PostCardBinding;
import com.example.myinstagram.databinding.StoryRecylerCardBinding;
import com.example.myinstagram.interfaces.HTTPClient;
import com.example.myinstagram.interfaces.HttpCallBack;
import com.example.myinstagram.models.Post;
import com.example.myinstagram.models.Story;
import com.example.myinstagram.network.HTTPClientFactory;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;

public class HomeFragment extends Fragment implements HttpCallBack {

    private static final String TAG = HomeFragment.class.getName();
    private List<Post> postsData;
    private List<Story> storiesData;
    private PostsAdapter postsAdapter;
    private StoriesAdapter storiesAdapter;
    private FragmentHomeBinding fragmentHomeBinding;
    private Context context;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
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

        final String POSTS_GET_URL = "https://jsonblob.com/api/jsonblob/4074c5dc-2dd1-11e9-8c29-6d3427129fcf";
        ConnectivityManager connectivityManager = null;
        NetworkInfo networkInfo = null;
        if(getActivity() != null)
            connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            HTTPClient okHTTPUtil = HTTPClientFactory.getOKHTTPUtil();
            okHTTPUtil.makeHTTPGetRequest(POSTS_GET_URL, this);
        }
    }

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
            updatePosts(postJSONMapper.getPosts());
        } catch (IOException e) {
            Log.e(TAG, "ResponseBody to String conversion failed : ", e);
        }
    }

    private void updatePosts(Post[] allPosts) {
        int i = 0;
        final int POST_TO_FETCH = 15;
        for(Post post: allPosts) {
            this.postsData.add(post);
            if(++i == POST_TO_FETCH)
                break;
        }
        if(getActivity() != null) {
            getActivity().runOnUiThread( () -> postsAdapter.notifyDataSetChanged() );
        }
    }

    public void createStoryRecyclerInPostRecycler(StoryRecylerCardBinding storyRecylerCardBinding) {
        storyRecylerCardBinding.storyRecyclerCard.setLayoutManager(
                new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false));
        this.storiesAdapter = new StoriesAdapter(this.context, this.storiesData);
        storyRecylerCardBinding.storyRecyclerCard.setAdapter(this.storiesAdapter);
        initializeStoryData();
    }

    private void initializeStoryData() {
        this.storiesData.clear();
        String[] userName = context.getResources().getStringArray(R.array.story_user_name);
        String[] imageURLs = context.getResources().getStringArray(R.array.story_image_url);

        for (int i = 0; i < userName.length; i++) {
            this.storiesData.add(new Story(userName[i], imageURLs[i]));
        }
        this.storiesAdapter.notifyDataSetChanged();
    }

    public void onLikeIconClick(PostCardBinding postCardBinding, Post postClicked) {
        if(postClicked.isLikeStatus()) {
            postClicked.setLikeStatus(false);
            postCardBinding.likeIcon.setBackgroundResource(R.drawable.baseline_favorite_border_black_18);
            postClicked.setLikes(postClicked.getLikes() - 1);
            updateLikeCount(postCardBinding, postClicked.getLikes());
        } else {
            postClicked.setLikeStatus(true);
            postCardBinding.likeIcon.setBackgroundResource(R.drawable.baseline_favorite_black_18);
            postClicked.setLikes(postClicked.getLikes() + 1);
            updateLikeCount(postCardBinding, postClicked.getLikes());
        }
        this.postsAdapter.notifyDataSetChanged();
    }

    private void updateLikeCount(PostCardBinding postCardBinding, int likeCount) {
        if(likeCount == 0) {
            postCardBinding.likes.setVisibility(View.GONE);
        } else {
            postCardBinding.likes.setVisibility(View.VISIBLE);
            postCardBinding.likes.setText(context.getResources()
                    .getQuantityString(R.plurals.like, likeCount, likeCount));
        }
    }
}
