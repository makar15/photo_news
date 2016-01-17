package com.example.makarov.photonews.network;

import java.net.MalformedURLException;
import java.net.URL;

public class NextPageUrlSaverTag implements NextPageUrlSaver {

    private String mNextUrl;
    private boolean mNextLoading = false;

    public void setUrl(String nextUrl) {
        mNextUrl = nextUrl;
        mNextLoading = true;
    }

    public URL getUrl() {
        try {
            mNextLoading = false;
            return new URL(mNextUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean getNextLoading() {
        return mNextLoading;
    }
}
