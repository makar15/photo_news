package com.example.makarov.photonews.network;

import com.example.makarov.photonews.PhotoNewsApp;
import com.example.makarov.photonews.models.Location;
import com.example.makarov.photonews.network.robospice.PhotoNewsLocationRequest;
import com.example.makarov.photonews.network.robospice.model.PhotoNewsList;
import com.example.makarov.photonews.utils.UrlInstaUtils;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.listener.RequestListener;

import java.net.URL;

public class PostFinderLocation implements PostFinder {

    private URL mUrlSavePhotosSearch;
    private Location mLocation;
    private boolean mNextLoading = true;

    private PhotoNewsLocationRequest mPostRequestImage;
    private NextPageUrlSaver mNextPageUrlSaver;

    public PostFinderLocation(Location location) {
        mLocation = location;
        mNextPageUrlSaver = new NextPageUrlSaverLocation(mLocation);
    }

    public void requestPhotos(RequestListener<PhotoNewsList> requestListener) {
        mUrlSavePhotosSearch = UrlInstaUtils.getUrlPhotosSearch(mLocation);
        mPostRequestImage = new PhotoNewsLocationRequest(mUrlSavePhotosSearch, mNextPageUrlSaver);

        PhotoNewsApp.getApp().getSpiceManager().execute(mPostRequestImage,
                mPostRequestImage.createCacheKey(), DurationInMillis.ONE_MINUTE, requestListener);
    }

    public void nextRequestPhotos(RequestListener<PhotoNewsList> requestListener) {

        URL nextUrl = mNextPageUrlSaver.getUrl();

        if (nextUrl != null && !mUrlSavePhotosSearch.getQuery().equals(nextUrl.getQuery())) {
            mUrlSavePhotosSearch = nextUrl;
            mPostRequestImage =
                    new PhotoNewsLocationRequest(mUrlSavePhotosSearch, mNextPageUrlSaver);

            PhotoNewsApp.getApp().getSpiceManager().execute(mPostRequestImage,
                    mPostRequestImage.createCacheKey(),
                    DurationInMillis.ONE_MINUTE, requestListener);
            return;
        }

        mNextLoading = false;
    }

    public boolean isNextLoading() {
        return mNextLoading;
    }
}
