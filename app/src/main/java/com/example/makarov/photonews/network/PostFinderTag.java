package com.example.makarov.photonews.network;

import com.example.makarov.photonews.PhotoNewsApp;
import com.example.makarov.photonews.network.robospice.TagRequest;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;
import com.example.makarov.photonews.utils.UrlInstaUtils;
import com.octo.android.robospice.request.listener.RequestListener;

import java.net.URL;

public class PostFinderTag implements PostFinder {

    private final NextPageUrlSaver mNextPageUrlSaver;
    private final String mLineTag;

    private TagRequest mTagRequest;
    private URL mUrlTag;

    public PostFinderTag(String lineTag) {
        mLineTag = lineTag;
        mNextPageUrlSaver = new NextPageUrlSaverTag();
    }

    public void requestPhotos(RequestListener<MediaPostList> requestListener) {
        mUrlTag = UrlInstaUtils.getUrlPhotosTag(mLineTag);
        mTagRequest = new TagRequest(mUrlTag, mNextPageUrlSaver);

        PhotoNewsApp.getApp().getSpiceManager().execute(mTagRequest,
                null, Long.parseLong(null), requestListener);
    }

    public boolean nextRequestPhotos(RequestListener<MediaPostList> requestListener) {

        if (mNextPageUrlSaver.getNextLoading()) {
            URL nextUrl = mNextPageUrlSaver.getUrl();

            if (nextUrl != null && !mUrlTag.equals(nextUrl)) {
                mUrlTag = nextUrl;
                mTagRequest = new TagRequest(mUrlTag, mNextPageUrlSaver);

                PhotoNewsApp.getApp().getSpiceManager().execute(mTagRequest,
                        null, Long.parseLong(null), requestListener);
            }
            return true;
        }
        return false;
    }
}
