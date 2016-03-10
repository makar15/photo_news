package com.example.makarov.photonews.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.database.MediaPostDbAdapter;
import com.example.makarov.photonews.di.AppInjector;
import com.example.makarov.photonews.models.MediaPost;
import com.melnykov.fab.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * generates a list of photos, with updating
 */
public class PhotoResultAdapter extends RecyclerView.Adapter<PhotoResultAdapter.ResultViewHolder> {

    private final int TYPE_HOLDER_MEDIA_POST = 0;
    private final int TYPE_HOLDER_SAVE_MEDIA_POST = 1;

    private final List<MediaPost> mMediaPosts = new ArrayList<>();
    private final MediaPostDbAdapter mMediaPostDbAdapter;

    public PhotoResultAdapter(List<MediaPost> mediaPosts) {
        mMediaPosts.addAll(mediaPosts);
        mMediaPostDbAdapter = AppInjector.get().getMediaPostDbAdapter();
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_media_post, parent, false);

        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, final int position) {

        String item = getItem(position).getUrlAddress();
        Picasso.with(holder.itemView.getContext())
                .load(item)
                .into(holder.icon);

        holder.author.setText(getItem(position).getAuthor());
        holder.countLikes.setText(String.valueOf(getItem(position).getCountLikes()));
        holder.operationMediaPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMediaPost(getItem(position));
                mMediaPostDbAdapter.close();
            }
        });
/*
        Picasso.with(holder.itemView.getContext())
                .load(item)
                .into(new ImageTarget(position));*/
    }

    @Override
    public int getItemCount() {
        return mMediaPosts.size();
    }

    private MediaPost getItem(int position) {
        return mMediaPosts.get(position);
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.icon)
        ImageView icon;
        @Bind(R.id.like)
        ImageView like;
        @Bind(R.id.author)
        TextView author;
        @Bind(R.id.count_likes)
        TextView countLikes;
        @Bind(R.id.media_post_btn)
        FloatingActionButton operationMediaPost;

        public ResultViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    private void addMediaPost(MediaPost mediaPost) {
        mMediaPostDbAdapter.open().add(mediaPost);
    }

    public void update(List<MediaPost> newMediaPosts) {
        for (MediaPost tempPost : newMediaPosts) {
            addItem(tempPost);
        }
        notifyItemRangeChanged(0, mMediaPosts.size());
    }

    public void addItem(MediaPost mediaPost) {
        mMediaPosts.add(mediaPost);
    }

}

