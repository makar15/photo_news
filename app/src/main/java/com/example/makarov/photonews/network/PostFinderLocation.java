package com.example.makarov.photonews.network;

import com.example.makarov.photonews.PhotoNewsApp;
import com.example.makarov.photonews.models.Address;
import com.example.makarov.photonews.network.robospice.PhotoNewsLocationRequest;
import com.example.makarov.photonews.network.robospice.model.PhotoNewsList;
import com.example.makarov.photonews.utils.UrlInstaUtils;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.listener.RequestListener;

import java.net.URL;

public class PostFinderLocation implements PostFinder {

    private URL mUrlSavePhotosSearch;
    private Address mAddress;

    private PhotoNewsLocationRequest mPostRequestImage;
    private NextPageUrlSaver mNextPageUrlSaver;

    public PostFinderLocation(Address address) {
        mAddress = address;
        mNextPageUrlSaver = new NextPageUrlSaverLocation(mAddress);
    }

    public void requestPhotos(RequestListener<PhotoNewsList> requestListener) {
        mUrlSavePhotosSearch = UrlInstaUtils.getUrlPhotosSearch(mAddress);
        mPostRequestImage = new PhotoNewsLocationRequest(mUrlSavePhotosSearch, mNextPageUrlSaver);

        PhotoNewsApp.getApp().getSpiceManager().execute(mPostRequestImage, mPostRequestImage.createCacheKey(),
                DurationInMillis.ONE_MINUTE, requestListener);
    }

    public void nextRequestPhotos(RequestListener<PhotoNewsList> requestListener) {
        mUrlSavePhotosSearch = mNextPageUrlSaver.getUrl();
        mPostRequestImage = new PhotoNewsLocationRequest(mUrlSavePhotosSearch, mNextPageUrlSaver);

        PhotoNewsApp.getApp().getSpiceManager().execute(mPostRequestImage, mPostRequestImage.createCacheKey(),
                DurationInMillis.ONE_MINUTE, requestListener);
    }

}
