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

    private Context mContext;
    
    private List<Post> postsData;
    private List<Story> storiesData;
    private PostsAdapter postsAdapter;
    private StoriesAdapter storiesAdapter;

    FragmentHomeBinding mFragmentHomeBinding;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postsData = new ArrayList<>();
        storiesData = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mFragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        mFragmentHomeBinding.homePostRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        return mFragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        postsAdapter = new PostsAdapter(mContext, postsData);
        mFragmentHomeBinding.homePostRecyclerView.setAdapter(postsAdapter);

        mFragmentHomeBinding.homeStoryRecyclerView.setLayoutManager(
                new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        storiesAdapter = new StoriesAdapter(mContext, storiesData);
        mFragmentHomeBinding.homeStoryRecyclerView.setAdapter(storiesAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializePostData();
        initializeStoryData();
    }

    private void initializePostData() {
        String[] userName = mContext.getResources().getStringArray(R.array.post_user_name);
        String[] postDescription = mContext.getResources().getStringArray(R.array.post_description);
        TypedArray sportsImageResources = mContext.getResources().obtainTypedArray(R.array.post_image_resource);
        for(int i=0;i<userName.length;i++){
            postsData.add(new Post(userName[i], sportsImageResources.getResourceId(i, 0), postDescription[i]));
        }

        postsAdapter.notifyDataSetChanged();
        sportsImageResources.recycle();
    }

    private void initializeStoryData() {
        String[] userName = mContext.getResources().getStringArray(R.array.story_user_name);
        TypedArray storiesImageResource = mContext.getResources().obtainTypedArray(R.array.story_image_resource);

        for (int i=0; i<userName.length; i++) {
            storiesData.add(new Story(userName[i], storiesImageResource.getResourceId(i ,0)));
        }
        storiesAdapter.notifyDataSetChanged();
        storiesImageResource.recycle();

    }
}
