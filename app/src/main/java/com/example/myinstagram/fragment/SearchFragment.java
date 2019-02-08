package com.example.myinstagram.fragment;

import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myinstagram.R;
import com.example.myinstagram.adapter.GridsAdapter;
import com.example.myinstagram.databinding.FragmentSearchBinding;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private List<Integer> gridImageResources;
    private GridsAdapter gridsAdapter;

    public static SearchFragment newInstance() {

        Bundle args = new Bundle();

        SearchFragment fragment = new SearchFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        FragmentSearchBinding fragmentSearchBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_search, container, false);

        fragmentSearchBinding.searchGridRecyclerView
                .setLayoutManager(new GridLayoutManager(getContext(), 2));
        gridImageResources = new ArrayList<>();
        gridsAdapter = new GridsAdapter(getContext(), gridImageResources);
        fragmentSearchBinding.searchGridRecyclerView.setAdapter(gridsAdapter);

        initializeGridData();
        return fragmentSearchBinding.getRoot();
    }

    private void initializeGridData() {
        TypedArray gridImage = getResources().obtainTypedArray(R.array.post_image_resource);

        for (int i=0; i<gridImage.length(); i++) {
            gridImageResources.add(gridImage.getResourceId(i ,0));
        }
        gridsAdapter.notifyDataSetChanged();
        gridImage.recycle();
    }
}
