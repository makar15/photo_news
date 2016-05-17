package com.example.makarov.photonews.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.makarov.photonews.FactoryPostFinder;
import com.example.makarov.photonews.R;
import com.example.makarov.photonews.adapters.MediaPostsAdapter;
import com.example.makarov.photonews.di.AppInjector;
import com.example.makarov.photonews.network.postfinders.PostFinder;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class PhotoFragment extends Fragment {

    private static final int PHOTO_LOADING_PORTION = 10;

    public static final String TAG_KEY = "PhotoResultTag";
    public static final String LOCATION_KEY = "PhotoResultLocation";
    public static final String MEDIA_POST_KEY = "MediaPostResult";

    @Bind(R.id.lv_photo_result)
    SuperRecyclerView mSuperRecyclerView;

    @Inject
    FactoryPostFinder mFactoryPostFinder;

    private final MediaPostsAdapter mMediaPostsAdapter = new MediaPostsAdapter();

    private PostFinder mPostFinder;

    private final OnMoreListener mOnScrollsListListener = new OnMoreListener() {
        @Override
        public void onMoreAsked(int overallItemsCount,
                                int itemsBeforeMore, int maxLastVisiblePosition) {
            if (!mPostFinder.nextRequestPhotos(new MediaPostsRequestListener(mMediaPostsAdapter))) {
                mSuperRecyclerView.hideMoreProgress();
            }
        }
    };

    @Nullable
    protected abstract PostFinder createPostFinder();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_photo_result_fragment, null);
        ButterKnife.bind(this, v);
        AppInjector.get().inject(this);
        setLayoutManager();
        mSuperRecyclerView.setAdapter(mMediaPostsAdapter);
        mPostFinder = createPostFinder();

        mPostFinder.requestPhotos(new MediaPostsRequestListener(mMediaPostsAdapter));
        mSuperRecyclerView.setupMoreListener(mOnScrollsListListener, PHOTO_LOADING_PORTION);

        return v;
    }

    private void setLayoutManager() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        mSuperRecyclerView.setLayoutManager(layoutManager);
    }

    public static class MediaPostsRequestListener implements RequestListener<MediaPostList> {

        private final MediaPostsAdapter mMediaPostsAdapter;

        public MediaPostsRequestListener(MediaPostsAdapter adapter) {
            mMediaPostsAdapter = adapter;
        }

        @Override
        public void onRequestFailure(SpiceException spiceException) {

        }

        @Override
        public void onRequestSuccess(MediaPostList mediaPostList) {
            if (mediaPostList != null && !mediaPostList.getMediaPosts().isEmpty()) {
                mMediaPostsAdapter.update(mediaPostList.getMediaPosts());
            }
        }
    }
}
