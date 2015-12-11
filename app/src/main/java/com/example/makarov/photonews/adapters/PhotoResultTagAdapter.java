package com.example.makarov.photonews.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.makarov.photonews.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by makarov on 08.12.15.
 */
public class PhotoResultTagAdapter extends BaseAdapter {

    private LayoutInflater mLInflater;
    private ImageView icon;
    private Context mContext;

    private List<String> mUrlPhotos;

    public PhotoResultTagAdapter(Context context, List<String> urlPhotos) {
        mContext = context;
        mLInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mUrlPhotos = urlPhotos;
    }

    @Override
    public int getCount() {
        return mUrlPhotos.size();
    }

    @Override
    public Object getItem(int position) {
        return mUrlPhotos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = mLInflater.inflate(R.layout.item_photo_tag, parent, false);
        }

        icon = (ImageView) view.findViewById(R.id.icon);
        String tempUrlPhoto = getUrlPhoto(position);
        downloadImage(tempUrlPhoto);

        return view;
    }

    public String getUrlPhoto(int position) {
        return ((String) getItem(position));
    }

    private void downloadImage(String item) {
        Picasso.with(mContext)
                .load(item)
                .into(icon);
    }
}
