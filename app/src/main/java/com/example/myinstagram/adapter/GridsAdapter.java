package com.example.myinstagram.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.myinstagram.R;
import com.example.myinstagram.databinding.GridCardBinding;

import java.util.List;

public class GridsAdapter extends RecyclerView.Adapter<GridsAdapter.GridViewHolder> {

    private Context context;
    private List<String> gridImageURLs;

    public GridsAdapter(Context context, List<String> gridImageURLs) {
        this.context = context;
        this.gridImageURLs = gridImageURLs;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new GridViewHolder(
                LayoutInflater.from(this.context).inflate(R.layout.grid_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder gridViewHolder, int i) {
        String currentGridImageURL = this.gridImageURLs.get(i);
        gridViewHolder.bindTo(currentGridImageURL);
    }

    @Override
    public int getItemCount() {
        return this.gridImageURLs.size();
    }

    class GridViewHolder extends RecyclerView.ViewHolder {

        GridCardBinding gridCardBinding;
        private GridViewHolder(@NonNull View itemView) {
            super(itemView);
            this.gridCardBinding = GridCardBinding.bind(itemView);
        }

        private void bindTo(String imageURL) {
            Glide.with(context).load(imageURL).into(this.gridCardBinding.searchGridContent);
        }
    }
}
