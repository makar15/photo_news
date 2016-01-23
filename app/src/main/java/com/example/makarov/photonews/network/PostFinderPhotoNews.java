package com.example.makarov.photonews.network;

import com.example.makarov.photonews.PhotoNewsApp;
import com.example.makarov.photonews.network.robospice.PhotoNewsImageRequest;
import com.example.makarov.photonews.network.robospice.model.PhotoNewsList;
import com.example.makarov.photonews.utils.UrlInstaUtils;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.listener.RequestListener;

import java.net.URL;

public class PostFinderPhotoNews implements PostFinder {

    private URL mUrlSavePhotosTemp;
    private String mTemp;

    private PhotoNewsImageRequest mPostRequestImage;
    private NextPageUrlSaver mNextPageUrlSaver;

    public PostFinderPhotoNews() {

    }

    public void requestPhotos(RequestListener<PhotoNewsList> requestListener) {

    }

    public boolean nextRequestPhotos(RequestListener<PhotoNewsList> requestListener) {

        return false;
    }
}
