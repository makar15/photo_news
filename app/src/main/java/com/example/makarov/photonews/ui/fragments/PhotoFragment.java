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
import com.example.makarov.photonews.network.PostFinder;
import com.example.makarov.photonews.network.robospice.model.PhotoNewsList;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

public abstract class PhotoFragment extends Fragment {

    public static final String PHOTO_RESULT_TAG_KEY = "photo_result_tag";

    private SuperRecyclerView mSuperRecyclerView;
    private PhotoResultAdapter mPhotoAdapter;

    protected abstract PostFinder createPostFinder();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_photo_result_fragment, null);

        mSuperRecyclerView = (SuperRecyclerView) v.findViewById(R.id.lv_photo_result);
        setLayoutManagerForRecyclerView();

        final PostFinder postFinder = createPostFinder();

        postFinder.requestPhotos(new RequestListener<PhotoNewsList>() {
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

        int LOAD_PHOTO = 10;
        mSuperRecyclerView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {

                if (!postFinder.nextRequestPhotos(new RequestListener<PhotoNewsList>() {
                    @Override
                    public void onRequestFailure(SpiceException spiceException) {

                    }

                    @Override
                    public void onRequestSuccess(PhotoNewsList photoNews) {
                        if (photoNews != null) {
                            mPhotoAdapter.update(photoNews.getPhotoNewsPosts());
                        }
                    }
                })) {
                    mSuperRecyclerView.hideMoreProgress();
                }
            }
        }, LOAD_PHOTO);

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
            //TODO add and save a photo to my basket
            mPhotoAdapter = new PhotoResultAdapter(photoNews);
            mSuperRecyclerView.setAdapter(mPhotoAdapter);
        }
    }

}
