package com.example.makarov.photonews.network;

import com.example.makarov.photonews.network.robospice.model.PhotoNewsList;
import com.octo.android.robospice.request.listener.RequestListener;

public interface PostFinder {

    void requestPhotos(RequestListener<PhotoNewsList> requestListener);

    void nextRequestPhotos(RequestListener<PhotoNewsList> requestListener);

    boolean isNextLoading();
}
