package com.example.makarov.photonews.adapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.makarov.photonews.adapters.scrolling.OnLoadMoreListener;
import com.example.makarov.photonews.R;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by makarov on 14.12.15.
 */
public class PhotoResultTagAdapter extends RecyclerView.Adapter<PhotoResultTagAdapter.ResultViewHolder> {

    private List<String> mUrlPhotos;

    private int visibleThreshold = 10;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    public static class ResultViewHolder extends RecyclerView.ViewHolder {

        public ImageView icon;

        public ResultViewHolder(View v) {
            super(v);
            icon = (ImageView) v.findViewById(R.id.icon);
        }
    }

    public PhotoResultTagAdapter(List<String> urlPhotos, RecyclerView recyclerView) {
        mUrlPhotos = urlPhotos;

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                    .getLayoutManager();

            recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager
                            .findLastVisibleItemPosition();
                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (onLoadMoreListener != null) {
                            try {
                                onLoadMoreListener.onLoadMore();
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                        loading = true;
                    }
                }
            });
        }
    }

    @Override
    public ResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo_tag, parent, false);

        ResultViewHolder resultViewHolder = new ResultViewHolder(view);

        return resultViewHolder;
    }

    @Override
    public void onBindViewHolder(ResultViewHolder holder, int position) {

        //downloadImage(holder.itemView.getContext(), holder.icon, mUrlPhotos.get(position));

        String item = mUrlPhotos.get(position);
        Picasso.with(holder.itemView.getContext())
                .load(item)
                .into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return mUrlPhotos.size();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }
}

