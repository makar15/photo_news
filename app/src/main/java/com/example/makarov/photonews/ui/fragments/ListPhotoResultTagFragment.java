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
import com.example.makarov.photonews.models.PhotoNewsPost;
import com.example.makarov.photonews.network.PostFinderTag;
import com.example.makarov.photonews.network.robospice.model.PhotoNewsList;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

public class ListPhotoResultTagFragment extends Fragment {

    public static final String PHOTO_RESULT_TAG_KEY = "photo_result_tag";

    private SuperRecyclerView mSuperRecyclerView;
    private PhotoResultAdapter mPhotoAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_photo_result_fragment, null);

        mSuperRecyclerView = (SuperRecyclerView) v.findViewById(R.id.lv_photo_result);
        setLayoutManagerForRecyclerView();

        String lineTag = getArgumentsBundleLineTag(getArguments());
        final PostFinderTag postFinderTag = new PostFinderTag(lineTag);

        postFinderTag.requestPhotos(new RequestListener<PhotoNewsList>() {
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

                postFinderTag.nextRequestPhotos(new RequestListener<PhotoNewsList>() {
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

    private String getArgumentsBundleLineTag(Bundle savedInstanceState) {
        if (savedInstanceState == null)
            return null;
        if (!savedInstanceState.containsKey(ListPhotoResultTagFragment.PHOTO_RESULT_TAG_KEY))
            return null;
        return savedInstanceState.getString(ListPhotoResultTagFragment.PHOTO_RESULT_TAG_KEY);

    }
}
