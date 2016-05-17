package com.example.makarov.photonews.network.postfinders;

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

    private String mUrlLocation;

    public PostFinderLocation(Location location, SpiceManager spiceManager,
                              MediaPostParser mediaPostParser) {
        mLocation = location;
        mSpiceManager = spiceManager;
        mMediaPostParser = mediaPostParser;

        mUrlSaver = new NextPageUrlSaverLocation(mLocation);
    }

    public boolean requestPhotos(RequestListener<MediaPostList> requestListener) {
        mUrlLocation = UrlInstaUtils.getUrlPhotosLocation(mLocation);
        LocationRequest request = new LocationRequest(mMediaPostParser, mUrlLocation, mUrlSaver,
                mLocation);

        mSpiceManager.execute(request, null, DurationInMillis.ONE_MINUTE, requestListener);
        return true;
    }

    public boolean nextRequestPhotos(RequestListener<MediaPostList> requestListener) {
        if (!mUrlSaver.isNextLoading()) {
            return false;
        }

        String nextUrl = mUrlSaver.getUrl();
        if (nextUrl != null && !mUrlLocation.equals(nextUrl)) {
            mUrlLocation = nextUrl;
            LocationRequest request = new LocationRequest(mMediaPostParser, mUrlLocation,
                    mUrlSaver, mLocation);

            mSpiceManager.execute(request, null, DurationInMillis.ONE_MINUTE, requestListener);
        }
        return true;
    }
}
