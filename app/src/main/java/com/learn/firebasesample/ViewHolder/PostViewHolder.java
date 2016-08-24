package com.learn.firebasesample.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.learn.firebasesample.R;
import com.learn.firebasesample.model.Post;

/**
 * Created by CIPL0224 on 8/23/2016.
 */
public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;

    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = (TextView) itemView.findViewById(R.id.txt_title);
        authorView = (TextView) itemView.findViewById(R.id.txt_author_name);
        bodyView = (TextView) itemView.findViewById(R.id.txt_body);
    }

    public void bindToPost(Post post) {
        titleView.setText(post.title);
        authorView.setText(post.author);
        bodyView.setText(post.body);
    }
}