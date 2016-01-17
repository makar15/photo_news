package com.example.makarov.photonews.network;

import com.example.makarov.photonews.models.PhotoNewsPost;
import com.example.makarov.photonews.network.robospice.model.PhotoNewsList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Parsing {

    private final String PHOTO_STANDART_RESOLUTION = "standard_resolution";

    //получить URL картинки из JSON
    private String getUrlImage(JSONArray jsonArray, int indexImage) throws JSONException {
        JSONObject imageJsonObject = jsonArray.getJSONObject(indexImage)
                .getJSONObject("images").getJSONObject(PHOTO_STANDART_RESOLUTION);
        return imageJsonObject.getString("url");
    }

    private String getAuthor(JSONArray jsonArray, int indexImage) throws JSONException {
        JSONObject authorJSONObject = jsonArray.getJSONObject(indexImage)
                .getJSONObject("user");
        return authorJSONObject.getString("username");
    }

    private int getCountLikes(JSONArray jsonArray, int indexImage) throws JSONException {
        JSONObject countLikesJSONObject = jsonArray.getJSONObject(indexImage)
                .getJSONObject("likes");
        return countLikesJSONObject.getInt("count");
    }

    public PhotoNewsList jsonToPhotoNews(JSONObject json) throws IOException, JSONException {

        PhotoNewsList photoNews = new PhotoNewsList();

        try {
            JSONArray jsonArray = json.getJSONArray("data");
            int sizeArray = jsonArray.length();
            int NUMBER_SINGLE_QUERY = 20;
            int count = 0;

            if (sizeArray < NUMBER_SINGLE_QUERY) {
                NUMBER_SINGLE_QUERY = sizeArray;
            }

            while (count != NUMBER_SINGLE_QUERY) {
                String urlImage = getUrlImage(jsonArray, count);
                String author = getAuthor(jsonArray, count);
                int countLikes = getCountLikes(jsonArray, count);

                PhotoNewsPost photoNewsPost = new PhotoNewsPost(author, urlImage, countLikes);
                photoNews.getPhotoNewsPosts().add(photoNewsPost);

                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photoNews;
    }

}
