package com.example.makarov.photonews.utils;

import com.example.makarov.photonews.models.Location;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlInstaUtils {

    /**
     * /tags/tag-name/media/recent
     * https://api.instagram.com/v1/tags/{tag-name}/media/recent?access_token=ACCESS-TOKEN
     * по тэгу получить фоточки
     */
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

    /**
     * /media/search
     * https://api.instagram.com/v1/media/search?lat=48.858844&lng=2.294351&distance=2500
     * &access_token=ACCESS-TOKEN
     * по lat и lng и дистанции(до 5000 м) получить фоточки
     */
    public static URL getUrlPhotosLocation(Location location) {

        String url = Constants.VERSION_API_URL + "/media/search?"
                + "lat=" + location.getLatitude() + "&lng=" + location.getLongitude()
                + "&distance=" + location.getRadiusSearch()
                + "&access_token=" + Constants.ACCESS_TOKEN;
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * /media/media-id
     * https://api.instagram.com/v1/media/{media-id}?access_token=ACCESS-TOKEN
     * по id поста получить фоточку
     */
    public static URL getUrlMediaPost(String idPost) {

        String url = Constants.VERSION_API_URL + "/media/" + idPost +
                "?access_token=" + Constants.ACCESS_TOKEN;
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
