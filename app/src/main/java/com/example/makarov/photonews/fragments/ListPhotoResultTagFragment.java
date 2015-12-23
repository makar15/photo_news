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
import com.example.makarov.photonews.models.PhotoNewsPost;
import com.example.makarov.photonews.network.PostFinder;
import com.example.makarov.photonews.network.robospice.model.PhotoNewsList;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

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

        mRecyclerView = (RecyclerView) v.findViewById(R.id.lv_photo_result_tag);
        setLayoutManagerForRecyclerView();

        String lineTag = getArgumentsBundleLineTag(getArguments());
        final PostFinder postFinder = new PostFinder(lineTag);

        postFinder.requestPhotosTag(new RequestListener<PhotoNewsList>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(PhotoNewsList photoNews) {
                setAdapterForRecyclerView(photoNews.getPhotoNewsPosts());
            }
        });

        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore() {

                postFinder.nextRequestPhotosTag(new RequestListener<PhotoNewsList>() {
                    @Override
                    public void onRequestFailure(SpiceException spiceException) {

                    }

                    @Override
                    public void onRequestSuccess(PhotoNewsList photoNews) {
                        mPhotoAdapter.update(photoNews.getPhotoNewsPosts());
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
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void setAdapterForRecyclerView(List<PhotoNewsPost> photoNews) {
        mPhotoAdapter = new PhotoResultTagAdapter(photoNews);
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
