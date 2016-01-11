package com.example.makarov.photonews.adapters.scrolling;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        //Returns the number of items in the adapter bound to the parent RecyclerView.
        int totalItemCount = mLinearLayoutManager.getItemCount();
        //Returns the adapter position of the last visible view.
        int lastVisibleItem = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();

        if (lastVisibleItem >= totalItemCount - 1) {
            onLoadMore();
        }
    }

    public abstract void onLoadMore();
}
