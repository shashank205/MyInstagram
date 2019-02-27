package com.example.myinstagram.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myinstagram.R;
import com.example.myinstagram.databinding.PostCardBinding;
import com.example.myinstagram.databinding.StoryRecylerCardBinding;
import com.example.myinstagram.fragments.HomeFragment;
import com.example.myinstagram.models.Post;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    private static final int VIEW_TYPE_STORY = 1;
    private static final int VIEW_TYPE_POST = 2;
    private static final int STORY_POSITION = 0;
    private List<Post> postsData;
    private Context context;
    private HomeFragment homeFragment;

    public PostsAdapter(List<Post> postsData, HomeFragment homeFragment) {
        this.postsData = postsData;
        this.context = homeFragment.getContext();
        this.homeFragment = homeFragment;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i == VIEW_TYPE_POST) {
            View postCardView = LayoutInflater
                    .from(this.context).inflate(R.layout.post_card, viewGroup, false);
            PostCardBinding.bind(postCardView);
            return new PostViewHolder(postCardView);
        }
        else {
            View storyRecyclerCardView = LayoutInflater
                    .from(this.context).inflate(R.layout.story_recyler_card, viewGroup, false);
            StoryRecylerCardBinding.bind(storyRecyclerCardView);
            return new PostViewHolder(storyRecyclerCardView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
        int adapterPosition = postViewHolder.getAdapterPosition();
        if(postViewHolder.getItemViewType() == VIEW_TYPE_POST  && !this.postsData.isEmpty()) {
            Post currentPost;
            if(adapterPosition < STORY_POSITION) {
                currentPost = this.postsData.get(adapterPosition);
            } else {
                currentPost = this.postsData.get(adapterPosition-1);
            }

            postViewHolder.populate(currentPost, postViewHolder);
        } else if(postViewHolder.getItemViewType() == VIEW_TYPE_STORY){
            StoryRecylerCardBinding storyRecylerCardBinding = DataBindingUtil.getBinding(postViewHolder.itemView);
            homeFragment.createStoryRecyclerInPostRecycler(storyRecylerCardBinding);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(position == STORY_POSITION) {
            return VIEW_TYPE_STORY;
        } else {
            return VIEW_TYPE_POST;
        }
    }

    @Override
    public int getItemCount() {
        return this.postsData.size() + 1;
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        private PostViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        void populate(Post currentPost, PostViewHolder postViewHolder) {
            PostCardBinding postCardBinding = DataBindingUtil.getBinding(postViewHolder.itemView);
            if(postCardBinding != null) {
                postCardBinding.setPost(currentPost);
                postCardBinding.likeIcon
                        .setOnClickListener(v -> homeFragment.onLikeIconClick(postCardBinding, currentPost));
            }

        }
    }
}
