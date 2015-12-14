package com.example.makarov.photonews.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by makarov on 11.12.15.
 */
public class Parsing {

    private String nextMaxId;

    //открыть соединения для получения потока данных
    public void openHttpUrlConnection(HttpURLConnection urlConnection, URL url) throws IOException {
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
    }

    //получить входящий поток данных
    public String getStreamUrl(URL url) throws IOException {
        InputStream inputStream = url.openConnection().getInputStream();
        return streamToString(inputStream);
    }

    //преобразовать поток в строку
    private String streamToString(InputStream is) throws IOException {
        String str = "";

        if (is != null) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                reader.close();
            } finally {
                is.close();
            }

            str = stringBuilder.toString();
        }

        return str;
    }

    //преобразование строку в JSON
    public JSONArray getStringToJSONArray(String response) throws JSONException, IOException {
        JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
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
        JSONObject next_url = (JSONObject)jsonObject.get("pagination");
        nextMaxId = (String) next_url.get("next_max_tag_id");
    }

    public String getNextMaxId() {
        return nextMaxId;
    }

}
