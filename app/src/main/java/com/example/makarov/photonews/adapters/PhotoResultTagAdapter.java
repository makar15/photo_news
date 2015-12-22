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

/**
 * Created by makarov on 14.12.15.
 * generates a list of photos, with updating
 */
public class PhotoResultTagAdapter extends RecyclerView.Adapter<PhotoResultTagAdapter.ResultViewHolder> {

    private List<PhotoNewsPost> mPhotoNews = new ArrayList<>();

    public PhotoResultTagAdapter(List<PhotoNewsPost> urlPhotos) {
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

    }

    @Override
    public int getItemCount() {
        return mPhotoNews.size();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {

        public ImageView icon;
        public ImageView like;
        public TextView author;
        public TextView countLikes;

        public ResultViewHolder(View v) {
            super(v);

            icon = (ImageView) v.findViewById(R.id.icon);
            like = (ImageView) v.findViewById(R.id.like);
            author = (TextView) v.findViewById(R.id.author);
            countLikes = (TextView) v.findViewById(R.id.count_likes);

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

