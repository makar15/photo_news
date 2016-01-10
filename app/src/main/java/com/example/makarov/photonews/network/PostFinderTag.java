package com.example.makarov.photonews.network;

import com.example.makarov.photonews.PhotoNewsApp;
import com.example.makarov.photonews.network.robospice.PhotoNewsSpiceRequest;
import com.example.makarov.photonews.network.robospice.model.PhotoNewsList;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.listener.RequestListener;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by makarov on 21.12.15.
 */
public class PostFinderTag {

    private final String VERSION_API_URL = "https://api.instagram.com/v1";
    private final String ACCESS_TOKEN = "175770414.98d6195.7e44bb27685141c5ba834cb7dcd67625";

    private final Parsing mParsing;
    private URL mUrlSavePhotosTag;
    private String mLineTag;

    private PhotoNewsSpiceRequest postRequest;

    public PostFinderTag(String lineTag) {
        mLineTag = lineTag;
        mParsing = new Parsing();
    }

    public void requestPhotosTag(RequestListener<PhotoNewsList> requestListener) {
        mUrlSavePhotosTag = getGenerationUrlAccessPhotosTag(mLineTag);
        postRequest = new PhotoNewsSpiceRequest(mUrlSavePhotosTag, mParsing);

        PhotoNewsApp.getApp().getSpiceManager().execute(postRequest, postRequest.createCacheKey(),
                DurationInMillis.ONE_MINUTE, requestListener);
    }

    public void nextRequestPhotosTag(RequestListener<PhotoNewsList> requestListener) {
        mUrlSavePhotosTag = getGenerationNextUrlAccessPhotosTag(mLineTag);
        postRequest = new PhotoNewsSpiceRequest(mUrlSavePhotosTag, mParsing);

        PhotoNewsApp.getApp().getSpiceManager().execute(postRequest, postRequest.createCacheKey(),
                DurationInMillis.ONE_MINUTE, requestListener);
    }

    //получить URL для загрузки изображений по тэгу
    private URL getGenerationUrlAccessPhotosTag(String lineTag) {

        String url = VERSION_API_URL + "/tags/" + lineTag +
                "/media/recent/?access_token=" + ACCESS_TOKEN;
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    //получить следующий URL для загрузки изображений по тэгу
    private URL getGenerationNextUrlAccessPhotosTag(String lineTag) {

        String nextUrl = VERSION_API_URL + "/tags/" + lineTag + "/media/recent/?access_token=" +
                ACCESS_TOKEN + "&max_tag_id=" + mParsing.getNextMaxId();
        try {
            return new URL(nextUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
