package com.example.makarov.photonews.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.models.PhotoNewsPost;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * generates a list of photos, with updating
 */
public class PhotoResultAdapter extends RecyclerView.Adapter<PhotoResultAdapter.ResultViewHolder> {

    private List<PhotoNewsPost> mPhotoNews = new ArrayList<>();

    public PhotoResultAdapter(List<PhotoNewsPost> urlPhotos) {
        mPhotoNews.addAll(urlPhotos);
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo_tag, parent, false);

        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {

        String item = mPhotoNews.get(position).getUrlAddress();
        Picasso.with(holder.itemView.getContext())
                .load(item)
                .into(holder.icon);

        holder.author.setText(mPhotoNews.get(position).getAuthor());
        holder.countLikes.setText(String.valueOf(mPhotoNews.get(position).getCountLikes()));
/*
        Picasso.with(holder.itemView.getContext())
                .load(item)
                .into(new ImageTarget(position));*/
    }

    @Override
    public int getItemCount() {
        return mPhotoNews.size();
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

        public ResultViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public void update(List<PhotoNewsPost> newPhotoNews) {
        for (PhotoNewsPost tempPhotoItem : newPhotoNews) {
            addItem(tempPhotoItem);
        }
        notifyItemRangeChanged(0, mPhotoNews.size());
    }

    public void addItem(PhotoNewsPost photoNewsPost) {
        mPhotoNews.add(photoNewsPost);
    }

}

