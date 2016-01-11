package com.example.makarov.photonews.network;

import com.example.makarov.photonews.utils.Constants;

import java.net.MalformedURLException;
import java.net.URL;

public class NextPageUrlSaverLocation implements NextPageUrlSaver {

    private String mUrl;

    public void setUrl(String url) {
        mUrl = url;
    }

    public URL getUrl() {

        //https://api.instagram.com/v1/locations/50576751/media/recent?access_token=175770414.98d6195.7e44bb27685141c5ba834cb7dcd67625

        String nextUrl = Constants.VERSION_API_URL + "/locations/" + "/media/recent/?access_token=" +
                Constants.ACCESS_TOKEN + "&max_tag_id=" + mUrl;
        try {
            return new URL(nextUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
