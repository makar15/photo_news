package com.example.makarov.photonews.network;

import com.example.makarov.photonews.network.robospice.model.MediaPostList;
import com.octo.android.robospice.request.listener.RequestListener;

public interface PostFinder {

    void requestPhotos(RequestListener<MediaPostList> requestListener);

    boolean nextRequestPhotos(RequestListener<MediaPostList> requestListener);

}
