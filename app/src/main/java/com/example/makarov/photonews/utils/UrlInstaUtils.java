package com.example.makarov.photonews.utils;

import com.example.makarov.photonews.models.Address;

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

    public static URL getUrlPhotosSearch(Address address) {

        String url = Constants.VERSION_API_URL + "/media/search?" + "lat="
                + address.getLatitude() + "&lng=" + address.getLongitude() + "&distance=5000" +
                "&access_token=" + Constants.ACCESS_TOKEN;
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
