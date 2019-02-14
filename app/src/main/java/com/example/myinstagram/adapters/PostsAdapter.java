package com.example.myinstagram.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.myinstagram.R;
import com.example.myinstagram.databinding.PostCardBinding;
import com.example.myinstagram.models.Post;

import java.util.List;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.PostViewHolder> {

    private List<Post> postsData;
    private Context context;

    public PostsAdapter(Context context, List<Post> postsData) {
        this.postsData = postsData;
        this.context = context;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PostViewHolder(
                LayoutInflater.from(this.context).inflate(R.layout.post_card, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i) {
        Post currentPost = this.postsData.get(i);
        postViewHolder.bindTo(currentPost);
    }

    @Override
    public int getItemCount() {
        return this.postsData.size();
    }

    class PostViewHolder extends RecyclerView.ViewHolder {

        private PostCardBinding postCardBinding;

        private PostViewHolder(@NonNull View itemView) {
            super(itemView);
            this.postCardBinding = PostCardBinding.bind(itemView);
        }

        void bindTo(Post currentPost) {
            Glide.with(context).load(currentPost.getUser().getAvatarUrl()).into(this.postCardBinding.avatarUrl);
            this.postCardBinding.userName.setText(currentPost.getUser().getName());
            this.postCardBinding.location.setText(currentPost.getLocation());
            Glide.with(context).load(currentPost.getImageUrl()).into(this.postCardBinding.imageUrl);
            this.postCardBinding.caption.setText(currentPost.getCaption());

            if(currentPost.isLikeStatus()) {
                this.postCardBinding.likeIcon.setBackgroundResource(R.drawable.baseline_favorite_black_18);
            }

            if(currentPost.getLikes() == 0) {
                this.postCardBinding.likes.setVisibility(View.GONE);
            } else {
                this.postCardBinding.likes.setText(context.getResources()
                        .getQuantityString(R.plurals.like, currentPost.getLikes(), currentPost.getLikes()));
            }

            if(currentPost.getComments() == 0) {
                this.postCardBinding.comments.setVisibility(View.GONE);
            } else {
                this.postCardBinding.comments.setText(context.getResources()
                        .getQuantityString(R.plurals.comment, currentPost.getComments(), currentPost.getComments()));
            }

            if(currentPost.getLocation().equals("")) {
                ConstraintLayout.LayoutParams newLayoutParams = (ConstraintLayout.LayoutParams) this.postCardBinding.userName.getLayoutParams();
                float dpFactor = context.getResources().getDisplayMetrics().density;
                newLayoutParams.topMargin = (int)(24 * dpFactor);
                this.postCardBinding.userName.setLayoutParams(newLayoutParams);
            }
        }
    }
}
