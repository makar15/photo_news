package com.example.makarov.photonews.network;

import com.example.makarov.photonews.utils.Constants;

import java.net.MalformedURLException;
import java.net.URL;

public class NextPageUrlSaverTag implements NextPageUrlSaver {

    private String mUrl;
    private String mNameTag;

    public NextPageUrlSaverTag(String nameTag) {
        mNameTag = nameTag;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public URL getUrl() {

        String nextUrl = Constants.VERSION_API_URL + "/tags/" + mNameTag + "/media/recent/?access_token=" +
                Constants.ACCESS_TOKEN + "&max_tag_id=" + mUrl;
        try {
            return new URL(nextUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
