package com.example.myinstagram.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
        this.postsAdapter = new PostsAdapter(this.context, this.postsData);
        this.fragmentHomeBinding.homePostRecyclerView.setAdapter(this.postsAdapter);

        this.fragmentHomeBinding.homeStoryRecyclerView.setLayoutManager(
                new LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false));
        this.storiesAdapter = new StoriesAdapter(this.context, this.storiesData);
        this.fragmentHomeBinding.homeStoryRecyclerView.setAdapter(this.storiesAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializePostData();
        initializeStoryData();
    }

    private void initializePostData() {
        String[] userName = context.getResources().getStringArray(R.array.post_user_name);
        String[] postDescription = context.getResources().getStringArray(R.array.post_description);
        TypedArray sportsImageResources = context.getResources().obtainTypedArray(R.array.post_image_resource);
        for(int i=0;i<userName.length;i++){
            this.postsData.add(new Post(userName[i], sportsImageResources.getResourceId(i, 0), postDescription[i]));
        }

        this.postsAdapter.notifyDataSetChanged();
        sportsImageResources.recycle();
    }

    private void initializeStoryData() {
        String[] userName = context.getResources().getStringArray(R.array.story_user_name);
        TypedArray storiesImageResource = context.getResources().obtainTypedArray(R.array.story_image_resource);

        for (int i=0; i<userName.length; i++) {
            this.storiesData.add(new Story(userName[i], storiesImageResource.getResourceId(i ,0)));
        }
        this.storiesAdapter.notifyDataSetChanged();
        storiesImageResource.recycle();

    }
}
