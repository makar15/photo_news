package com.example.makarov.photonews.utils;

import com.example.makarov.photonews.models.Location;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlInstaUtils {

    public static URL getUrlPhotosTag(String lineTag) {

        String url = Constants.VERSION_API_URL + "/tags/" + lineTag +
                "/media/recent/?access_token=" + Constants.ACCESS_TOKEN;
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static URL getUrlPhotosSearch(Location location) {

        //TODO create control distance variable
        String url = Constants.VERSION_API_URL + "/media/search?" + "lat="
                + location.getLatitude() + "&lng=" + location.getLongitude() + "&distance=2500" +
                "&access_token=" + Constants.ACCESS_TOKEN;
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
