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
        //TODO check NullPointerException imageJsonObject
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
            JSONArray mJsonArray = json.getJSONArray("data");
            int NUMBER_SINGLE_QUERY = 20;
            int count = 0;

            while (count != NUMBER_SINGLE_QUERY) {
                String urlImage = getUrlImage(mJsonArray, count);
                String author = getAuthor(mJsonArray, count);
                int countLikes = getCountLikes(mJsonArray, count);

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
