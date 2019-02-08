package com.example.myinstagram.fragment;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
    private Context context;
    private FragmentSearchBinding fragmentSearchBinding;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    public SearchFragment() {
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
        this.gridImageResources = new ArrayList<>();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.fragmentSearchBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_search, container, false);
        return this.fragmentSearchBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.fragmentSearchBinding.searchGridRecyclerView
                .setLayoutManager(new GridLayoutManager(this.context, 2));
        this.gridsAdapter = new GridsAdapter(this.context, this.gridImageResources);
        this.fragmentSearchBinding.searchGridRecyclerView.setAdapter(this.gridsAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeGridData();
    }

    private void initializeGridData() {
        TypedArray gridImage = context.getResources().obtainTypedArray(R.array.post_image_resource);

        for (int i=0; i<gridImage.length(); i++) {
            this.gridImageResources.add(gridImage.getResourceId(i ,0));
        }
        this.gridsAdapter.notifyDataSetChanged();
        gridImage.recycle();
    }
}
