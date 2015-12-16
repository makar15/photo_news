package com.example.makarov.photonews.network;

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
 * Created by makarov on 15.12.15.
 */
public class UrlToJsonObject {

    //открыть соединения для получения потока данных
    public void openHttpUrlConnection(HttpURLConnection urlConnection, URL url) throws IOException {
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();
    }

    //получить входящий поток данных
    public String convertString(URL url) throws IOException {
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

    //преобразование строку в JSONObject
    public JSONObject getStringToJSONObject(String response) throws JSONException, IOException {
        JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
        return jsonObject;
    }
}
