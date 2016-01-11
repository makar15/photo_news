package com.example.makarov.photonews.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.adapters.PhotoResultAdapter;
import com.example.makarov.photonews.models.Address;
import com.example.makarov.photonews.models.PhotoNewsPost;
import com.example.makarov.photonews.network.PostFinderLocation;
import com.example.makarov.photonews.network.robospice.model.PhotoNewsList;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

public class ListPhotoResultLocationFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private PhotoResultAdapter mPhotoAdapter;
    private LinearLayoutManager mLayoutManager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_photo_result_tag_fragment, null);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.lv_photo_result_tag);
        setLayoutManagerForRecyclerView();

        double[] pointLocation = getArgumentsBundleLocation(getArguments());
        Address address = new Address(pointLocation[0], pointLocation[1]);
        final PostFinderLocation postFinderLocation = new PostFinderLocation(address);

        postFinderLocation.requestPhotos(new RequestListener<PhotoNewsList>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(PhotoNewsList photoNews) {
                setAdapterForRecyclerView(photoNews.getPhotoNewsPosts());
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
        mPhotoAdapter = new PhotoResultAdapter(photoNews);
        mRecyclerView.setAdapter(mPhotoAdapter);
    }

    private void initializeHistoryRecyclerView() {
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private double[] getArgumentsBundleLocation(Bundle savedInstanceState) {
        if (savedInstanceState == null)
            return null;
        if (!savedInstanceState.containsKey(GoogleMapFragment.GOOGLE_MAP_KEY))
            return null;
        return savedInstanceState.getDoubleArray(GoogleMapFragment.GOOGLE_MAP_KEY);

    }
}
