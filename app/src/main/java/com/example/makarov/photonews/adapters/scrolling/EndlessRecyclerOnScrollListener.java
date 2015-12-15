package com.example.makarov.photonews.adapters.scrolling;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by makarov on 14.12.15.
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private final int VISIBLE_THRESHOLD = 5;
    private int visibleItemCount, firstVisibleItem, totalItemCount;
    private int countUpdate = 0;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        //Return the current number of child views attached to the parent RecyclerView.
        visibleItemCount = recyclerView.getChildCount();
        //Returns the number of items in the adapter bound to the parent RecyclerView.
        totalItemCount = mLinearLayoutManager.getItemCount();
        //Returns the adapter position of the first visible view.
        firstVisibleItem = mLinearLayoutManager.findFirstCompletelyVisibleItemPosition();

        if (firstVisibleItem >= (totalItemCount - VISIBLE_THRESHOLD)) {
            if (countUpdate == 0) {
                onLoadMore();
                countUpdate++;
            }
        }
    }

    public abstract void onLoadMore();
}
