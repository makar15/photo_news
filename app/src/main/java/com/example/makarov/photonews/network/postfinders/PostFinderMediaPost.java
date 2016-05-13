package com.example.makarov.photonews.network.postfinders;

import com.example.makarov.photonews.PhotoNewsApp;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;
import com.example.makarov.photonews.network.robospice.requests.MediaPostRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.listener.RequestListener;

public class PostFinderMediaPost implements PostFinder {

    private final SpiceManager mSpiceManager = PhotoNewsApp.getApp().getSpiceManager();

    private int mNumberRequest = 0;

    public boolean requestPhotos(RequestListener<MediaPostList> requestListener) {
        request(requestListener);
        return true;
    }

    public boolean nextRequestPhotos(RequestListener<MediaPostList> requestListener) {
        request(requestListener);
        return true;
    }

    private void request(RequestListener<MediaPostList> requestListener) {
        MediaPostRequest request = new MediaPostRequest(mNumberRequest);

        mSpiceManager.execute(request, null, DurationInMillis.ONE_MINUTE, requestListener);
        mNumberRequest++;
    }
}
