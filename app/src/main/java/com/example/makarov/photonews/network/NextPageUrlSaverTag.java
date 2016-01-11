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

        //TODO chang
        // pagination // "next_url": "https://api.instagram.com/v1/tags/mapping/media/recent?access_token=175770414.98d6195.7e44bb27685141c5ba834cb7dcd67625&max_tag_id=1160304315044362480"

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
