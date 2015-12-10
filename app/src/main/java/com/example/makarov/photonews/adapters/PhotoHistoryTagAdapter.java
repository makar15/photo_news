package com.example.makarov.photonews.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.makarov.photonews.R;

import java.util.List;

/**
 * Created by makarov on 08.12.15.
 */
public class PhotoHistoryTagAdapter extends BaseAdapter {

    private List<ImageView> mPhotos;
    private LayoutInflater mLInflater;

    public PhotoHistoryTagAdapter(Context context, List<ImageView> photos){
        mLInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPhotos = photos;
    }

    @Override
    public int getCount() {
        return mPhotos.size();
    }

    @Override
    public Object getItem(int position) {
        return mPhotos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            //получаем LayoutInflater для работы с layout-ресурсами
            view = mLInflater.inflate(R.layout.item_photo_tag, parent, false);
        }

        return view;
    }

    public ImageView getImageView(int position) {
        return ((ImageView) getItem(position));
    }

}
