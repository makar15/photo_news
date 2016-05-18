package com.example.makarov.photonews.network.postfinders;

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

    private String mUrl;

    public PostFinderTag(String lineTag, SpiceManager spiceManager,
                         MediaPostParser mediaPostParser) {
        mLineTag = lineTag;
        mSpiceManager = spiceManager;
        mMediaPostParser = mediaPostParser;

        mUrlSaver = new NextPageUrlSaverTag();
    }

    public boolean requestPosts(RequestListener<MediaPostList> requestListener) {
        mUrl = UrlInstaUtils.getUrlPhotosTag(mLineTag);
        startNetworkRequest(requestListener);
        return true;
    }

    public boolean nextRequestPosts(RequestListener<MediaPostList> requestListener) {
        if (!mUrlSaver.isNextLoading()) {
            return false;
        }
        String nextUrl = mUrlSaver.getUrl();
        if (nextUrl == null || mUrl.equals(nextUrl)) {
            return true;
        }
        mUrl = nextUrl;
        startNetworkRequest(requestListener);
        return true;
    }

    private void startNetworkRequest(RequestListener<MediaPostList> requestListener) {
        TagRequest request = new TagRequest(mMediaPostParser, mUrl, mUrlSaver);
        mSpiceManager.execute(request, null, DurationInMillis.ONE_MINUTE, requestListener);
    }
}
