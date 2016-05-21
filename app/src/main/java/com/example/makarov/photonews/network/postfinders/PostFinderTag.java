package com.example.makarov.photonews.network.postfinders;

import android.support.annotation.Nullable;

import com.example.makarov.photonews.network.MediaPostParser;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;
import com.example.makarov.photonews.network.robospice.requests.TagRequest;
import com.example.makarov.photonews.network.savers.NextPageUrlSaver;
import com.example.makarov.photonews.network.savers.NextPageUrlSaverTag;
import com.example.makarov.photonews.utils.UrlInstaUtils;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.request.listener.RequestListener;

public class PostFinderTag implements PostFinder {

    private final SpiceManager mSpiceManager;
    private final MediaPostParser mMediaPostParser;
    private final NextPageUrlSaver mUrlSaver;
    private final String mLineTag;

    @Nullable private String mUrl = null;

    public PostFinderTag(String lineTag, SpiceManager spiceManager,
                         MediaPostParser mediaPostParser) {
        mLineTag = lineTag;
        mSpiceManager = spiceManager;
        mMediaPostParser = mediaPostParser;

        mUrlSaver = new NextPageUrlSaverTag();
    }

    public boolean requestPosts(RequestListener<MediaPostList> requestListener) {
        if(mUrl == null) {
            mUrl = UrlInstaUtils.getUrlPhotosTag(mLineTag);
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
        TagRequest request = new TagRequest(mMediaPostParser, mUrl, mUrlSaver);
        mSpiceManager.execute(request, null, DurationInMillis.ONE_MINUTE, requestListener);
    }
}
