package com.example.makarov.photonews.network;

import java.net.MalformedURLException;
import java.net.URL;

public class NextPageUrlSaverTag implements NextPageUrlSaver {

    private String mNextUrl;

    public void setUrl(String nextUrl) {
        mNextUrl = nextUrl;
    }

    public URL getUrl() {
        try {
            return new URL(mNextUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
