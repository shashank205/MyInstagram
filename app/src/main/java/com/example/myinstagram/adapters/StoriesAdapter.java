package com.example.myinstagram.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myinstagram.R;
import com.example.myinstagram.databinding.StoryCardBinding;
import com.example.myinstagram.models.Story;

import java.util.List;

public class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.StoryViewHolder> {

    private List<Story> storiesData;
    private Context context;

    public StoriesAdapter(Context context, List<Story>storiesData) {
        this.storiesData = storiesData;
        this.context = context;
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View storyCardView = LayoutInflater.from(this.context).inflate(R.layout.story_card, viewGroup, false);
        return new StoryViewHolder(storyCardView);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder storyViewHolder, int i) {
        int adapterPosition = storyViewHolder.getAdapterPosition();
        Story currentStory = this.storiesData.get(adapterPosition);
        storyViewHolder.populate(currentStory);
    }

    @Override
    public int getItemCount() {
        return this.storiesData.size();
    }

    class StoryViewHolder extends RecyclerView.ViewHolder {

        private StoryCardBinding storyCardBinding;

        StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            this.storyCardBinding = StoryCardBinding.bind(itemView);
        }

        private void populate(Story currentStory) {
            this.storyCardBinding.setStory(currentStory);
        }
    }
}