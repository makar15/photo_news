package com.example.makarov.photonews.network;

import com.example.makarov.photonews.models.MediaPost;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MediaPostParser {

    private static final String PHOTO_STANDART_RESOLUTION = "standard_resolution";
    private static final int QUANTITY_SINGLE_QUERY = 20;

    public MediaPostList parse(JSONObject jsonObject) throws IOException, JSONException {
        MediaPostList list = new MediaPostList();
        List<MediaPost> posts = list.getMediaPosts();

        JSONArray jsonArray = jsonObject.getJSONArray("data");
        int sizeArray = jsonArray.length();
        int counter = 0;

        while (counter != Math.min(sizeArray, QUANTITY_SINGLE_QUERY)) {
            JSONObject json = jsonArray.getJSONObject(counter);

            String idMediaPost = getIdMediaPost(json);
            String urlImage = getUrlImage(json);
            String author = getAuthor(json);
            String profilePicture = getProfilePicture(json);
            int countLikes = getCountLikes(json);

            MediaPost mediaPost = new MediaPost(idMediaPost, author, profilePicture,
                    urlImage, countLikes);
            posts.add(mediaPost);
            counter++;
        }
        return list;
    }

    public void update(MediaPost mediaPost, JSONObject jsonObject)
            throws IOException, JSONException {
        JSONObject json = jsonObject.getJSONObject("data");

        mediaPost.setAuthor(getAuthor(json));
        mediaPost.setProfilePicture(getProfilePicture(json));
        mediaPost.setCountLikes(getCountLikes(json));
    }

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

    private static String getProfilePicture(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject("user").getString("profile_picture");
    }

    private static int getCountLikes(JSONObject jsonObject) throws JSONException {
        return jsonObject.getJSONObject("likes").getInt("count");
    }
}
