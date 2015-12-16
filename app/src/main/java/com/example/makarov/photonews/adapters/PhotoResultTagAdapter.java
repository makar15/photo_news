package com.example.makarov.photonews.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.makarov.photonews.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makarov on 14.12.15.
 * generates a list of photos, with updating
 */
public class PhotoResultTagAdapter extends RecyclerView.Adapter<PhotoResultTagAdapter.ResultViewHolder> {

    private List<String> mUrlPhotos = new ArrayList<>();

    public PhotoResultTagAdapter(List<String> urlPhotos) {
        mUrlPhotos.addAll(urlPhotos);
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo_tag, parent, false);

        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {

        String item = mUrlPhotos.get(position);
        Picasso.with(holder.itemView.getContext())
                .load(item)
                .into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return mUrlPhotos.size();
    }

    public static class ResultViewHolder extends RecyclerView.ViewHolder {

        public ImageView icon;

        public ResultViewHolder(View v) {
            super(v);

            icon = (ImageView) v.findViewById(R.id.icon);
        }
    }

    public void update(List<String> newAddList) {
        mUrlPhotos.addAll(newAddList);
    }

}

