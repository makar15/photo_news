package com.example.makarov.photonews.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.adapters.PhotoResultTagAdapter;
import com.example.makarov.photonews.adapters.scrolling.EndlessRecyclerOnScrollListener;
import com.example.makarov.photonews.network.PostFinder;

import java.util.List;

/**
 * Created by makarov on 08.12.15.
 */
public class ListPhotoResultTagFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PhotoResultTagAdapter mPhotoAdapter;
    private LinearLayoutManager mLayoutManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_photo_result_tag_fragment, null);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.lvPhotoResultTag);
        setLayoutManagerForRecyclerView();

        String lineTag = getArgumentsBundleLineTag(getArguments());
        final PostFinder postFinder = new PostFinder(lineTag);

        postFinder.requestPhotosTag(new PostFinder.SuccessLoadedUrls() {
            @Override
            public void onLoaded(List<String> urlImages) {
                setAdapterForRecyclerView(urlImages);
            }
        });

        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore() {

                postFinder.nextRequestPhotosTag(new PostFinder.SuccessLoadedUrls() {
                    @Override
                    public void onLoaded(List<String> urlImages) {

                        mPhotoAdapter.update(urlImages);
                        mPhotoAdapter.notifyDataSetChanged();
                        mLayoutManager.onItemsChanged(mRecyclerView);
                    }
                });
            }
        });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeHistoryRecyclerView();
    }

    private void setLayoutManagerForRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void setAdapterForRecyclerView(List<String> urlPhotos) {
        mPhotoAdapter = new PhotoResultTagAdapter(urlPhotos);
        mRecyclerView.setAdapter(mPhotoAdapter);
    }

    private void initializeHistoryRecyclerView() {
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private String getArgumentsBundleLineTag(Bundle savedInstanceState) {
        if (savedInstanceState == null)
            return null;
        if (!savedInstanceState.containsKey(OperationTagFragment.OPERATION_KEY))
            return null;
        return savedInstanceState.getString(OperationTagFragment.OPERATION_KEY);

    }
}
