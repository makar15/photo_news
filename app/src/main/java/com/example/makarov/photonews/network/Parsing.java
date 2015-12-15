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

    private String nextMaxId;

    //преобразование строку в JSONArray
    public JSONArray getJSONObjectToJSONArray(JSONObject jsonObject) throws JSONException, IOException {
        saveNextUrlAccessPhotosTag(jsonObject);
        return jsonObject.getJSONArray("data");
    }

    //получить URL картинки из JSON
    public String getUrlImage(JSONArray jsonArray, int indexImage, String resolution) throws JSONException {
        JSONObject imageJsonObject = jsonArray.getJSONObject(indexImage)
                .getJSONObject("images").getJSONObject(resolution);
        //TODO check NullPointerException imageJsonObject
        return imageJsonObject.getString("url");
    }

    public void saveNextUrlAccessPhotosTag(JSONObject jsonObject) throws JSONException, IOException {
        JSONObject next_url = (JSONObject) jsonObject.get("pagination");
        nextMaxId = (String) next_url.get("next_max_tag_id");
    }

    public String getNextMaxId() {
        return nextMaxId;
    }

}
