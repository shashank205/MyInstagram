package com.example.myinstagram.models;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myinstagram.R;

public class LayoutBindingUtil {

    private LayoutBindingUtil() {
        //Not called
    }

    @BindingAdapter("glideUrl")
    public static void setImageUrl(ImageView view, String imageUrl) {
        Context context = view.getContext();
        Glide.with(context).load(imageUrl).into(view);
    }

    @BindingAdapter("likeIcon")
    public static void setImageUrl(ImageView view, boolean isLiked) {
        if(isLiked)
            view.setBackgroundResource(R.drawable.baseline_favorite_black_18);
        else view.setBackgroundResource(R.drawable.baseline_favorite_border_black_18);
    }

    @BindingAdapter("topMargin")
    public static void setTopMargin(TextView textView, String location) {
        Context context = textView.getContext();
        float dpFactor = context.getResources().getDisplayMetrics().density;

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) textView.getLayoutParams();
        layoutParams.topMargin = (int) (14*dpFactor);
        if(location.equals("")) {
            layoutParams.topMargin = (int) (24*dpFactor);
        }
        textView.setLayoutParams(layoutParams);
    }
}
