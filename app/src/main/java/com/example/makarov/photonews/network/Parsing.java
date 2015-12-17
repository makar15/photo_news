package com.example.makarov.photonews.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by makarov on 11.12.15.
 */
//getNextPage -должен вернуть адрес след страницы и его запомнить в постфайндере
public class Parsing {

    private final String PHOTO_STANDART_RESOLUTION = "standard_resolution";

    private String nextMaxId;

    //преобразование строку в JSONArray
    public JSONArray getJSONObjectToJSONArray(JSONObject jsonObject) throws JSONException, IOException {
        saveNextUrlAccessPhotosTag(jsonObject);
        return jsonObject.getJSONArray("data");
    }

    //получить URL картинки из JSON
    public String getUrlImage(JSONArray jsonArray, int indexImage) throws JSONException {
        JSONObject imageJsonObject = jsonArray.getJSONObject(indexImage)
                .getJSONObject("images").getJSONObject(PHOTO_STANDART_RESOLUTION);
        //TODO check NullPointerException imageJsonObject
        return imageJsonObject.getString("url");
    }

    public String getAuthor(JSONArray jsonArray, int indexImage) throws JSONException {
        JSONObject authorJSONObject = jsonArray.getJSONObject(indexImage)
                .getJSONObject("user");
        return authorJSONObject.getString("username");
    }

    public int getCountLikes(JSONArray jsonArray, int indexImage) throws JSONException {
        JSONObject countLikesJSONObject = jsonArray.getJSONObject(indexImage)
                .getJSONObject("likes");
        return countLikesJSONObject.getInt("count");
    }

    private void saveNextUrlAccessPhotosTag(JSONObject jsonObject) throws JSONException, IOException {
        JSONObject next_url = (JSONObject) jsonObject.get("pagination");
        nextMaxId = (String) next_url.get("next_max_tag_id");
    }

    public String getNextMaxId() {
        return nextMaxId;
    }

}
