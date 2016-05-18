package com.example.makarov.photonews.network.postfinders;

import com.example.makarov.photonews.database.MediaPostDbAdapter;
import com.example.makarov.photonews.network.MediaPostParser;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;
import com.example.makarov.photonews.network.robospice.requests.MediaPostRequest;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.listener.RequestListener;

public class PostFinderMediaPost implements PostFinder {

    private final SpiceManager mSpiceManager;
    private final MediaPostParser mMediaPostParser;
    private final MediaPostDbAdapter mMediaPostDbAdapter;

    private int mNumberRequest = 0;

    public PostFinderMediaPost(SpiceManager spiceManager, MediaPostParser mediaPostParser,
                               MediaPostDbAdapter mediaPostDbAdapter) {
        mSpiceManager = spiceManager;
        mMediaPostParser = mediaPostParser;
        mMediaPostDbAdapter = mediaPostDbAdapter;
    }

    public boolean requestPosts(RequestListener<MediaPostList> requestListener) {
        request(requestListener);
        return true;
    }

    public boolean nextRequestPosts(RequestListener<MediaPostList> requestListener) {
        request(requestListener);
        return true;
    }

    private void request(RequestListener<MediaPostList> requestListener) {
        MediaPostRequest request = new MediaPostRequest(mMediaPostParser, mMediaPostDbAdapter,
                mNumberRequest);
        mSpiceManager.execute(request, null, DurationInMillis.ONE_MINUTE, requestListener);
        mNumberRequest++;
    }
}
