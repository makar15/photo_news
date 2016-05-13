package com.example.makarov.photonews.network.savers;

public class NextPageUrlSaverTag implements NextPageUrlSaver {

    private String mNextUrl;
    private boolean mIsNextLoading;

    public void setUrl(String nextUrl) {
        mNextUrl = nextUrl;
        mIsNextLoading = true;
    }

    public String getUrl() {
        mIsNextLoading = false;
        return mNextUrl;
    }

    public boolean isNextLoading() {
        return mIsNextLoading;
    }
}
