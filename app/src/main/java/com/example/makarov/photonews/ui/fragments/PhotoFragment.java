package com.example.makarov.photonews.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.makarov.photonews.FactoryPostFinder;
import com.example.makarov.photonews.R;
import com.example.makarov.photonews.adapters.PhotoResultAdapter;
import com.example.makarov.photonews.di.AppInjector;
import com.example.makarov.photonews.models.MediaPost;
import com.example.makarov.photonews.network.postfinders.PostFinder;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class PhotoFragment extends Fragment {

    public static final String PHOTO_RESULT_TAG_KEY = "photo_result_tag";
    public static final String PHOTO_RESULT_LOCATION_KEY = "photo_result_location";
    public static final String MEDIA_POST_RESULT_KEY = "media_post_result";

    @Bind(R.id.lv_photo_result)
    SuperRecyclerView mSuperRecyclerView;

    @Inject
    FactoryPostFinder mFactoryPostFinder;

    private PhotoResultAdapter mPhotoAdapter;

    protected abstract PostFinder createPostFinder();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_photo_result_fragment, null);
        ButterKnife.bind(this, v);
        AppInjector.get().inject(this);

        setLayoutManager();

        final PostFinder postFinder = createPostFinder();

        postFinder.requestPhotos(new RequestListener<MediaPostList>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(MediaPostList photoNews) {
                if (photoNews != null) {
                    setAdapter(photoNews.getMediaPosts());
                }
            }
        });

        final int LOAD_PHOTO = 10;
        mSuperRecyclerView.setupMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int numberOfItems, int numberBeforeMore, int currentItemPos) {

                if (!postFinder.nextRequestPhotos(new RequestListener<MediaPostList>() {
                    @Override
                    public void onRequestFailure(SpiceException spiceException) {

                    }

                    @Override
                    public void onRequestSuccess(MediaPostList photoNews) {
                        if (photoNews != null) {
                            mPhotoAdapter.update(photoNews.getMediaPosts());
                        }
                    }
                })) {
                    mSuperRecyclerView.hideMoreProgress();
                }
            }
        }, LOAD_PHOTO);

        return v;
    }

    private void setLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mSuperRecyclerView.setLayoutManager(layoutManager);
    }

    private void setAdapter(List<MediaPost> photoNews) {
        if (photoNews.isEmpty()) {
            Toast.makeText(getContext(), "not photo",
                    Toast.LENGTH_LONG).show();
        } else {
            //TODO add and save a photo to my basket
            mPhotoAdapter = new PhotoResultAdapter(photoNews);
            mSuperRecyclerView.setAdapter(mPhotoAdapter);
        }
    }

}
