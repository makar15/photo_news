package com.example.makarov.photonews.utils;

import com.example.makarov.photonews.models.Location;

public class UrlInstaUtils {

    /**
     * /tags/tag-name/media/recent
     * https://api.instagram.com/v1/tags/{tag-name}/media/recent?access_token=ACCESS-TOKEN
     * по тэгу получить фоточки
     */
    public static String getUrlPhotosTag(String lineTag) {
        return Constants.VERSION_API_URL + "/tags/" + lineTag +
                "/media/recent/?access_token=" + Constants.ACCESS_TOKEN;
    }

    /**
     * /media/search
     * https://api.instagram.com/v1/media/search?lat=48.858844&lng=2.294351&distance=2500
     * &access_token=ACCESS-TOKEN
     * по lat и lng и дистанции(до 5000 м) получить фоточки
     */
    public static String getUrlPhotosLocation(Location location) {
        return Constants.VERSION_API_URL + "/media/search?"
                + "lat=" + location.getLatitude() + "&lng=" + location.getLongitude()
                + "&distance=" + location.getRadiusSearch()
                + "&access_token=" + Constants.ACCESS_TOKEN;
    }

    /**
     * /media/media-id
     * https://api.instagram.com/v1/media/{media-id}?access_token=ACCESS-TOKEN
     * по id поста получить фоточку
     */
    public static String getUrlMediaPost(String idPost) {
        return Constants.VERSION_API_URL + "/media/" + idPost +
                "?access_token=" + Constants.ACCESS_TOKEN;
    }

    /**
     * /media/search
     * https://api.instagram.com/v1/media/search?lat=48.858844&lng=2.294351&distance=2500
     * &max_timestamp=1440755407&access_token=ACCESS-TOKEN
     * по lat, lng, дистанции(до 5000 м) и времени создания поста, получить фоточки
     */
    public static String getNextUrlPhotosLocation(Location location, String createdTime) {
        return Constants.VERSION_API_URL + "/media/search?"
                + "lat=" + location.getLatitude() + "&lng=" + location.getLongitude()
                + "&distance=" + location.getRadiusSearch() + "&max_timestamp=" + createdTime
                + "&access_token=" + Constants.ACCESS_TOKEN;
    }
}
