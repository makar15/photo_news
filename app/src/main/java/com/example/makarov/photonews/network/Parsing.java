package com.example.makarov.photonews.network;

import com.example.makarov.photonews.models.MediaPost;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class Parsing {

    private static final String PHOTO_STANDART_RESOLUTION = "standard_resolution";

    private static String getIdMediaPost(JSONObject jsonObject) throws JSONException {
        return jsonObject.getString("id");
    }

    private static String getUrlImage(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject("images").
                getJSONObject(PHOTO_STANDART_RESOLUTION).getString("url");
    }

    private static String getAuthor(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject("user").getString("username");
    }

    private static int getCountLikes(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject("likes").getInt("count");
    }

    public static MediaPostList jsonToMediaPosts(JSONObject json) throws IOException, JSONException {

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

    public static void jsonToUpdateMediaPost(MediaPost mediaPost, JSONObject json)
            throws IOException, JSONException {
        try {
            JSONObject jsonObject = json.getJSONObject("data");

            String author = getAuthor(jsonObject);
            int countLikes = getCountLikes(jsonObject);

            mediaPost.setAuthor(author);
            mediaPost.setCountLikes(countLikes);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
