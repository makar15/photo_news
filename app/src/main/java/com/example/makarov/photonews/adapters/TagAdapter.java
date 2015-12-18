package com.example.makarov.photonews.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.makarov.photonews.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makarov on 17.12.15.
 */
public class TagAdapter extends RecyclerView.Adapter<TagAdapter.TagViewHolder> {

    private List<String> mTags = new ArrayList<>();

    public TagAdapter(List<String> tags) {
        mTags = tags;
    }

    @Override
    public TagViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tag, parent, false);

        return new TagViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TagViewHolder holder, int position) {

        holder.nameTag.setText(mTags.get(position));
    }

    @Override
    public int getItemCount() {
        return mTags.size();
    }

    public static class TagViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTag;

        public TagViewHolder(View v) {
            super(v);

            nameTag = (TextView) v.findViewById(R.id.name_tag);

        }
    }
}
