package com.example.makarov.photonews.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

public class JsonUtils {

    //преобразование строку в JSONObject
    public static JSONObject getStringToJSONObject(String response) throws JSONException, IOException {
        JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
        return jsonObject;
    }
}
