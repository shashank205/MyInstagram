package com.example.myinstagram.fragment;

import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myinstagram.R;
import com.example.myinstagram.adapter.PostsAdapter;
import com.example.myinstagram.adapter.StoriesAdapter;
import com.example.myinstagram.databinding.FragmentHomeBinding;
import com.example.myinstagram.model.Post;
import com.example.myinstagram.model.Story;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private List<Post> postsData;
    private List<Story> storiesData;
    private PostsAdapter postsAdapter;
    private StoriesAdapter storiesAdapter;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentHomeBinding fragmentHomeBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_home, container, false);

        fragmentHomeBinding.homePostRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        postsData = new ArrayList<>();
        postsAdapter = new PostsAdapter(getContext(), postsData);
        fragmentHomeBinding.homePostRecyclerView.setAdapter(postsAdapter);

        fragmentHomeBinding.homeStoryRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        storiesData = new ArrayList<>();
        storiesAdapter = new StoriesAdapter(getContext(), storiesData);
        fragmentHomeBinding.homeStoryRecyclerView.setAdapter(storiesAdapter);

        initializePostData();
        initializeStoryData();
        return fragmentHomeBinding.getRoot();
    }

    private void initializePostData() {
        String[] userName = getResources().getStringArray(R.array.post_user_name);
        String[] postDescription = getResources().getStringArray(R.array.post_description);
        TypedArray sportsImageResources = getResources().obtainTypedArray(R.array.post_image_resource);
        for(int i=0;i<userName.length;i++){
            postsData.add(new Post(userName[i], sportsImageResources.getResourceId(i, 0), postDescription[i]));
        }

        postsAdapter.notifyDataSetChanged();
        sportsImageResources.recycle();
    }

    private void initializeStoryData() {
        String[] userName = getResources().getStringArray(R.array.story_user_name);
        TypedArray storiesImageResource = getResources().obtainTypedArray(R.array.story_image_resource);

        for (int i=0; i<userName.length; i++) {
            storiesData.add(new Story(userName[i], storiesImageResource.getResourceId(i ,0)));
        }
        storiesAdapter.notifyDataSetChanged();
        storiesImageResource.recycle();

    }
}
