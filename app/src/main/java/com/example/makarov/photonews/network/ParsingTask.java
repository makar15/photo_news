package com.example.makarov.photonews.network;

import android.os.AsyncTask;

import com.example.makarov.photonews.MyApp;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by makarov on 11.12.15.
 */
public class ParsingTask extends AsyncTask<URL, Void, List<String>> {

    private final int NUMBER_SINGLE_QUERY = 10;
    private final String PHOTO_STANDART_RESOLUTION = "standard_resolution";
    private final String PHOTO_LOW_RESOLUTION = "low_resolution";
    private final String PHOTO_THUMBNAIL = "thumbnail";

    private HttpURLConnection urlConnection = null;
    private JSONArray json;

    private List<String> urlImages = new ArrayList<>();
    private String resultJson = "";
    private String response;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected List<String> doInBackground(URL... params) {
        try {
            return parseUrlToList(params[0]);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<String> result) {
        super.onPostExecute(result);

    }

    private List<String> parseUrlToList(URL url) throws IOException, JSONException {

        try {
            int count = 0;

            response = MyApp.getApp().getParsing().getStreamUrl(url);
            json = MyApp.getApp().getParsing().getStringToJSONArray(response);
            MyApp.getApp().getParsing().openHttpUrlConnection(urlConnection, url);

            while (count != NUMBER_SINGLE_QUERY) {
                resultJson = MyApp.getApp().getParsing().getUrlImage(json, count, PHOTO_STANDART_RESOLUTION);

                urlImages.add(resultJson);
                count++;
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return urlImages;
    }

}
