package com.example.makarov.photonews.network;

import com.example.makarov.photonews.PhotoNewsApp;
import com.example.makarov.photonews.database.MediaPostDbAdapter;
import com.example.makarov.photonews.di.AppInjector;
import com.example.makarov.photonews.network.robospice.MediaPostRequest;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;
import com.example.makarov.photonews.utils.UrlInstaUtils;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.listener.RequestListener;

import java.net.URL;

public class PostFinderMediaPost implements PostFinder {

    private MediaPostRequest mMediaPostRequest;

    public PostFinderMediaPost() {

    }

    public void requestPhotos(RequestListener<MediaPostList> requestListener) {

        mMediaPostRequest = new MediaPostRequest();

        PhotoNewsApp.getApp().getSpiceManager().execute(mMediaPostRequest,
                mMediaPostRequest.createCacheKey(), DurationInMillis.ONE_MINUTE, requestListener);
    }

    public boolean nextRequestPhotos(RequestListener<MediaPostList> requestListener) {
        /*
        mMediaPostRequest = new MediaPostRequest();

        PhotoNewsApp.getApp().getSpiceManager().execute(mMediaPostRequest,
                mMediaPostRequest.createCacheKey(), DurationInMillis.ONE_MINUTE, requestListener);
                */
        return false;
    }
}
