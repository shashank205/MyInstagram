package com.example.myinstagram.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.myinstagram.R;
import com.example.myinstagram.databinding.StoryCardBinding;
import com.example.myinstagram.model.Story;

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
        return new StoryViewHolder(LayoutInflater.from(context).inflate(R.layout.story_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder storyViewHolder, int i) {
        Story currentStory = storiesData.get(i);
        storyViewHolder.bindTo(currentStory);

    }

    @Override
    public int getItemCount() {
        return storiesData.size();
    }

    class StoryViewHolder extends RecyclerView.ViewHolder {

        private StoryCardBinding storyCardBinding;

        StoryViewHolder(@NonNull View itemView) {
            super(itemView);
            storyCardBinding = StoryCardBinding.bind(itemView);
        }

        private void bindTo(Story currentStory) {
            Glide.with(context).load(currentStory.getImageResource()).into(storyCardBinding.storyContent);
            storyCardBinding.storyUserName.setText(currentStory.getUser().getName());

        }
    }
}
