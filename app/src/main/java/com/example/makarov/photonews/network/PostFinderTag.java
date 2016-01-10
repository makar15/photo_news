package com.example.makarov.photonews.network;

import com.example.makarov.photonews.PhotoNewsApp;
import com.example.makarov.photonews.network.robospice.PhotoNewsImageRequest;
import com.example.makarov.photonews.network.robospice.model.PhotoNewsList;
import com.example.makarov.photonews.utils.UrlInstaUtils;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.listener.RequestListener;

import java.net.URL;

public class PostFinderTag {

    private URL mUrlSavePhotosTag;
    private String mLineTag;

    private PhotoNewsImageRequest mPostRequestImage;
    private NextPageUrlSaver mNextPageUrlSaver;

    public PostFinderTag(String lineTag) {
        mLineTag = lineTag;

        mNextPageUrlSaver = new NextPageUrlSaver(mLineTag);
    }

    public void requestPhotosTag(RequestListener<PhotoNewsList> requestListener) {
        mUrlSavePhotosTag = UrlInstaUtils.getUrlPhotosTag(mLineTag);
        mPostRequestImage = new PhotoNewsImageRequest(mUrlSavePhotosTag, mNextPageUrlSaver);

        PhotoNewsApp.getApp().getSpiceManager().execute(mPostRequestImage, mPostRequestImage.createCacheKey(),
                DurationInMillis.ONE_MINUTE, requestListener);
    }

    public void nextRequestPhotosTag(RequestListener<PhotoNewsList> requestListener) {
        mUrlSavePhotosTag = mNextPageUrlSaver.getUrl();
        mPostRequestImage = new PhotoNewsImageRequest(mUrlSavePhotosTag, mNextPageUrlSaver);

        PhotoNewsApp.getApp().getSpiceManager().execute(mPostRequestImage, mPostRequestImage.createCacheKey(),
                DurationInMillis.ONE_MINUTE, requestListener);
    }

}
