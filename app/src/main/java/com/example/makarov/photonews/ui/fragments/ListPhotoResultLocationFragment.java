package com.example.makarov.photonews.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.makarov.photonews.R;
import com.example.makarov.photonews.adapters.PhotoResultAdapter;
import com.example.makarov.photonews.models.Address;
import com.example.makarov.photonews.models.PhotoNewsPost;
import com.example.makarov.photonews.network.PostFinderLocation;
import com.example.makarov.photonews.network.robospice.model.PhotoNewsList;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

public class ListPhotoResultLocationFragment extends Fragment {

    private SuperRecyclerView mSuperRecyclerView;
    private PhotoResultAdapter mPhotoAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_photo_result_fragment, null);

        mSuperRecyclerView = (SuperRecyclerView) v.findViewById(R.id.lv_photo_result);
        setLayoutManagerForRecyclerView();

        Address address = getArgumentsBundleLocation(getArguments());
        final PostFinderLocation postFinderLocation = new PostFinderLocation(address);

        postFinderLocation.requestPhotos(new RequestListener<PhotoNewsList>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(PhotoNewsList photoNews) {
                if (photoNews != null) {
                    setAdapterForRecyclerView(photoNews.getPhotoNewsPosts());
                }
            }
        });

        mSuperRecyclerView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {

                postFinderLocation.nextRequestPhotos(new RequestListener<PhotoNewsList>() {
                    @Override
                    public void onRequestFailure(SpiceException spiceException) {

                    }

                    @Override
                    public void onRequestSuccess(PhotoNewsList photoNews) {
                        if (photoNews != null) {
                            mPhotoAdapter.update(photoNews.getPhotoNewsPosts());
                        }
                    }
                });
            }
        }, 10);

        return v;
    }

    private void setLayoutManagerForRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mSuperRecyclerView.setLayoutManager(layoutManager);
    }

    private void setAdapterForRecyclerView(List<PhotoNewsPost> photoNews) {
        if (photoNews.isEmpty()) {
            Toast.makeText(getContext(), "not PHOTO",
                    Toast.LENGTH_LONG).show();
        } else {
            mPhotoAdapter = new PhotoResultAdapter(photoNews);
            mSuperRecyclerView.setAdapter(mPhotoAdapter);
        }
    }

    private Address getArgumentsBundleLocation(Bundle savedInstanceState) {
        if (savedInstanceState == null)
            return null;
        if (!savedInstanceState.containsKey(GoogleMapFragment.GOOGLE_MAP_KEY))
            return null;
        return savedInstanceState.getParcelable(GoogleMapFragment.GOOGLE_MAP_KEY);

    }
}
