package com.example.makarov.photonews.network.postfinders;

import com.example.makarov.photonews.network.robospice.model.MediaPostList;
import com.octo.android.robospice.request.listener.RequestListener;

public interface PostFinder {

    boolean requestPhotos(RequestListener<MediaPostList> requestListener);

    boolean nextRequestPhotos(RequestListener<MediaPostList> requestListener);

}
