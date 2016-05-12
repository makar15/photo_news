package com.example.makarov.photonews.network;

import com.example.makarov.photonews.PhotoNewsApp;
import com.example.makarov.photonews.network.robospice.MediaPostRequest;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;
import com.octo.android.robospice.request.listener.RequestListener;

public class PostFinderMediaPost implements PostFinder {

    private MediaPostRequest mMediaPostRequest;
    private int mNumberRequest = 0;

    public void requestPhotos(RequestListener<MediaPostList> requestListener) {
        mMediaPostRequest = new MediaPostRequest(mNumberRequest);

        PhotoNewsApp.getApp().getSpiceManager().execute(mMediaPostRequest,
                null, Long.parseLong(null), requestListener);
        mNumberRequest++;
    }
    //TODO start request too often!!
    public boolean nextRequestPhotos(RequestListener<MediaPostList> requestListener) {
        mMediaPostRequest = new MediaPostRequest(mNumberRequest);

        PhotoNewsApp.getApp().getSpiceManager().execute(mMediaPostRequest,
                null, Long.parseLong(null), requestListener);
        mNumberRequest++;

        return true;
    }
}
