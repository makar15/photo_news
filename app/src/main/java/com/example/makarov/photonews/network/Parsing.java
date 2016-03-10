package com.example.makarov.photonews.network;

import com.example.makarov.photonews.models.MediaPost;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class Parsing {

    private final String PHOTO_STANDART_RESOLUTION = "standard_resolution";

    private String getIdMediaPost(JSONObject jsonObject) throws JSONException {
        return jsonObject.getString("id");
    }

    private String getUrlImage(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject("images").
                getJSONObject(PHOTO_STANDART_RESOLUTION).getString("url");
    }

    private String getAuthor(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject("user").getString("username");
    }

    private int getCountLikes(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject("likes").getInt("count");
    }

    public MediaPostList jsonToMediaPosts(JSONObject json) throws IOException, JSONException {

        MediaPostList mediaPosts = new MediaPostList();
        List<MediaPost> posts = mediaPosts.getMediaPosts();

        try {
            JSONArray jsonArray = json.getJSONArray("data");
            int sizeArray = jsonArray.length();
            int NUMBER_SINGLE_QUERY = 20;
            int count = 0;

            if (sizeArray < NUMBER_SINGLE_QUERY) {
                NUMBER_SINGLE_QUERY = sizeArray;
            }

            while (count != NUMBER_SINGLE_QUERY) {

                JSONObject jsonObject = jsonArray.getJSONObject(count);

                String idMediaPost = getIdMediaPost(jsonObject);
                String urlImage = getUrlImage(jsonObject);
                String author = getAuthor(jsonObject);
                int countLikes = getCountLikes(jsonObject);

                MediaPost mediaPost = new MediaPost(idMediaPost, author, urlImage, countLikes);
                posts.add(mediaPost);

                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mediaPosts;
    }

    public MediaPost jsonToUpdateMediaPost(JSONObject json) throws IOException, JSONException {

        try {
            JSONObject jsonObject = json.getJSONObject("data");

            String idMediaPost = getIdMediaPost(jsonObject);
            String urlImage = getUrlImage(jsonObject);
            String author = getAuthor(jsonObject);
            int countLikes = getCountLikes(jsonObject);

            return new MediaPost(idMediaPost, author, urlImage, countLikes);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
