package com.example.myinstagram.fragment;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myinstagram.R;
import com.example.myinstagram.adapter.PostsAdapter;
import com.example.myinstagram.model.Post;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<Post> postsData;
    private PostsAdapter postsAdapter;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.homeRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        postsData = new ArrayList<>();
        postsAdapter = new PostsAdapter(getContext(), postsData);
        recyclerView.setAdapter(postsAdapter);
        initializeData();
        return view;
    }

    private void initializeData() {
        String[] userName = getResources().getStringArray(R.array.user_name);
        String[] postDescription = getResources().getStringArray(R.array.post_description);
        TypedArray sportsImageResources = getResources().obtainTypedArray(R.array.post_content);

        postsData.clear();

        for(int i=0;i<userName.length;i++){
            postsData.add(new Post(userName[i], sportsImageResources.getResourceId(i, 0), postDescription[i]));
        }

        postsAdapter.notifyDataSetChanged();
    }
}
