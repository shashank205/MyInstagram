package com.example.myinstagram.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
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
    private PostCardBinding adapterPostCardBinding;

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
            this.adapterPostCardBinding = PostCardBinding.bind(postCardView);
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
        if(postViewHolder.getItemViewType() == VIEW_TYPE_POST  && !this.postsData.isEmpty()) {
            Post currentPost;
            if(i < STORY_POSITION) {
                currentPost = this.postsData.get(i);
            } else {
                currentPost = this.postsData.get(i-1);
            }
            postViewHolder.populate(currentPost);
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

        private PostCardBinding postCardBinding;
        private PostViewHolder(@NonNull View itemView) {
            super(itemView);
            this.postCardBinding = adapterPostCardBinding;
        }

        void populate(Post currentPost) {
            Glide.with(context).load(currentPost.getUser().getAvatarUrl()).into(this.postCardBinding.avatarUrl);
            this.postCardBinding.userName.setText(currentPost.getUser().getName());
            this.postCardBinding.location.setText(currentPost.getLocation());
            Glide.with(context).load(currentPost.getImageUrl()).into(this.postCardBinding.imageUrl);
            this.postCardBinding.caption.setText(currentPost.getCaption());

            if(currentPost.isLikeStatus()) {
                this.postCardBinding.likeIcon.setBackgroundResource(R.drawable.baseline_favorite_black_18);
            } else {
                this.postCardBinding.likeIcon.setBackgroundResource(R.drawable.baseline_favorite_border_black_18);
            }

            if(currentPost.getLikes() == 0) {
                this.postCardBinding.likes.setVisibility(View.GONE);
            } else {
                this.postCardBinding.likes.setVisibility(View.VISIBLE);
                this.postCardBinding.likes.setText(context.getResources()
                        .getQuantityString(R.plurals.like, currentPost.getLikes(), currentPost.getLikes()));
            }

            if(currentPost.getComments() == 0) {
                this.postCardBinding.comments.setVisibility(View.GONE);
            } else {
                this.postCardBinding.comments.setVisibility(View.VISIBLE);
                this.postCardBinding.comments.setText(context.getResources()
                        .getQuantityString(R.plurals.comment, currentPost.getComments(), currentPost.getComments()));
            }

            float dpFactor = context.getResources().getDisplayMetrics().density;
            if(currentPost.getLocation().equals("")) {
                ConstraintLayout.LayoutParams newLayoutParams =
                        (ConstraintLayout.LayoutParams) this.postCardBinding.userName.getLayoutParams();
                newLayoutParams.topMargin = (int)(24 * dpFactor);
                this.postCardBinding.userName.setLayoutParams(newLayoutParams);
            } else {
                ConstraintLayout.LayoutParams newLayoutParams =
                        (ConstraintLayout.LayoutParams) this.postCardBinding.userName.getLayoutParams();
                newLayoutParams.topMargin = (int)(14 * dpFactor);
                this.postCardBinding.userName.setLayoutParams(newLayoutParams);
            }

            this.postCardBinding.likeIcon
                    .setOnClickListener(v -> homeFragment.onLikeIconClick(postCardBinding, currentPost));
        }
    }
}
