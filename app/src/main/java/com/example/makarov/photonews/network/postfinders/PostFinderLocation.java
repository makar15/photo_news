package com.example.makarov.photonews.network.postfinders;

import android.support.annotation.Nullable;

import com.example.makarov.photonews.models.Location;
import com.example.makarov.photonews.network.MediaPostParser;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;
import com.example.makarov.photonews.network.robospice.requests.LocationRequest;
import com.example.makarov.photonews.network.savers.NextPageUrlSaver;
import com.example.makarov.photonews.network.savers.NextPageUrlSaverLocation;
import com.example.makarov.photonews.utils.UrlInstaUtils;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.listener.RequestListener;

public class PostFinderLocation implements PostFinder {

    private final SpiceManager mSpiceManager;
    private final MediaPostParser mMediaPostParser;
    private final NextPageUrlSaver mUrlSaver;
    private final Location mLocation;

    @Nullable private String mUrl = null;

    public PostFinderLocation(Location location, SpiceManager spiceManager,
                              MediaPostParser mediaPostParser) {
        mLocation = location;
        mSpiceManager = spiceManager;
        mMediaPostParser = mediaPostParser;

        mUrlSaver = new NextPageUrlSaverLocation(mLocation);
    }

    public boolean requestPosts(RequestListener<MediaPostList> requestListener) {
        if(mUrl == null) {
            mUrl = UrlInstaUtils.getUrlPhotosLocation(mLocation);
            request(requestListener);
            return true;
        }
        if (!mUrlSaver.isNextLoading()) {
            return false;
        }
        String nextUrl = mUrlSaver.getUrl();
        if (nextUrl == null || mUrl.equals(nextUrl)) {
            return true;
        }
        mUrl = nextUrl;
        request(requestListener);
        return true;
    }

    private void request(RequestListener<MediaPostList> requestListener) {
        LocationRequest request = new LocationRequest(mMediaPostParser, mUrl, mUrlSaver,
                mLocation);
        mSpiceManager.execute(request, null, DurationInMillis.ONE_MINUTE, requestListener);
    }
}
