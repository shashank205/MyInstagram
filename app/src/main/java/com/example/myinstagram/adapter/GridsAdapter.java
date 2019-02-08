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
    private List<Integer> gridImageResources;

    public GridsAdapter(Context context, List<Integer> gridImageResources) {
        this.context = context;
        this.gridImageResources = gridImageResources;
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new GridViewHolder(
                LayoutInflater.from(this.context).inflate(R.layout.grid_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GridViewHolder gridViewHolder, int i) {
        Integer currentGridImageResource = this.gridImageResources.get(i);
        gridViewHolder.bindTo(currentGridImageResource);
    }

    @Override
    public int getItemCount() {
        return this.gridImageResources.size();
    }

    class GridViewHolder extends RecyclerView.ViewHolder {

        GridCardBinding gridCardBinding;
        private GridViewHolder(@NonNull View itemView) {
            super(itemView);
            this.gridCardBinding = GridCardBinding.bind(itemView);
        }

        private void bindTo(int contentResource) {
            Glide.with(context).load(contentResource).into(this.gridCardBinding.searchGridContent);
        }
    }
}
