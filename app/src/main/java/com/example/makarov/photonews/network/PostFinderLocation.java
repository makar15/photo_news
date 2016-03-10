package com.example.makarov.photonews.network;

import com.example.makarov.photonews.PhotoNewsApp;
import com.example.makarov.photonews.models.Location;
import com.example.makarov.photonews.network.robospice.LocationRequest;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;
import com.example.makarov.photonews.utils.UrlInstaUtils;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.listener.RequestListener;

import java.net.URL;

public class PostFinderLocation implements PostFinder {

    private final NextPageUrlSaver mNextPageUrlSaver;
    private final Location mLocation;

    private LocationRequest mLocationRequest;
    private URL mUrlLocation;

    public PostFinderLocation(Location location) {
        mLocation = location;
        mNextPageUrlSaver = new NextPageUrlSaverLocation(mLocation);
    }

    public void requestPhotos(RequestListener<MediaPostList> requestListener) {
        mUrlLocation = UrlInstaUtils.getUrlPhotosLocation(mLocation);
        mLocationRequest = new LocationRequest
                (mUrlLocation, mNextPageUrlSaver, mLocation);

        PhotoNewsApp.getApp().getSpiceManager().execute(mLocationRequest,
                mLocationRequest.createCacheKey(), DurationInMillis.ONE_MINUTE, requestListener);
    }

    public boolean nextRequestPhotos(RequestListener<MediaPostList> requestListener) {

        if (mNextPageUrlSaver.getNextLoading()) {
            URL nextUrl = mNextPageUrlSaver.getUrl();

            if (nextUrl != null && !mUrlLocation.equals(nextUrl)) {
                mUrlLocation = nextUrl;
                mLocationRequest = new LocationRequest
                        (mUrlLocation, mNextPageUrlSaver, mLocation);

                PhotoNewsApp.getApp().getSpiceManager().execute(mLocationRequest,
                        mLocationRequest.createCacheKey(),
                        DurationInMillis.ONE_MINUTE, requestListener);
            }
            return true;
        }

        return false;
    }
}
