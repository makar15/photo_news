package com.example.makarov.photonews.adapters.scrolling;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.net.MalformedURLException;

/**
 * Created by makarov on 14.12.15.
 */
public abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    private int visibleThreshold = 5;
    private int visibleItemCount, firstVisibleItem, totalItemCount;
    private boolean loading = true;

    private int previousTotal = 0;
    private int current_page = 1;

    private LinearLayoutManager mLinearLayoutManager;

    public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        //Return the current number of child views attached to the parent RecyclerView.
        visibleItemCount = recyclerView.getChildCount();
        //Returns the number of items in the adapter bound to the parent RecyclerView.
        totalItemCount = mLinearLayoutManager.getItemCount();
        //Returns the adapter position of the first visible view.
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {

            try {
                current_page++;
                onLoadMore(current_page);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            loading = true;
        }
    }

    public abstract void onLoadMore(int current_page) throws MalformedURLException;
}
