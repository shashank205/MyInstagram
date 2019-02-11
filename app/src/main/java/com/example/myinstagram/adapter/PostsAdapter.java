package com.example.myinstagram.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.myinstagram.R;
import com.example.myinstagram.databinding.PostCardBinding;
import com.example.myinstagram.model.Post;

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
            this.postCardBinding.userName.setText(currentPost.getUser().getName());
            Glide.with(context).load(currentPost.getImageURL()).into(this.postCardBinding.postContent);
            this.postCardBinding.postDescription.setText(currentPost.getDescription());
        }
    }
}
