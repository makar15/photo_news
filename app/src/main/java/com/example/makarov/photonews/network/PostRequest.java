package com.example.makarov.photonews.network;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by makarov on 11.12.15.
 * URL tag -> convert JSONObject
 */
public class PostRequest extends AsyncTask<URL, Void, JSONObject> {

    private PostRequest.SuccessLoadedJson mSuccessLoadedJson;
    private UrlToJsonObject mUrlToJsonObject;

    private HttpURLConnection urlConnection = null;
    private JSONObject jsonObject;

    public PostRequest(PostRequest.SuccessLoadedJson successLoadedJson) {

        mSuccessLoadedJson = successLoadedJson;
        mUrlToJsonObject = new UrlToJsonObject();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected JSONObject doInBackground(URL... params) {
        try {
            return getJson(params[0]);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        super.onPostExecute(result);

        if (mSuccessLoadedJson != null) {
            mSuccessLoadedJson.onLoaded(result);
        }
    }

    private JSONObject getJson(URL url) throws IOException, JSONException {

        try {
            String response = mUrlToJsonObject.getStreamUrl(url);
            jsonObject = mUrlToJsonObject.getStringToJSONObject(response);
            mUrlToJsonObject.openHttpUrlConnection(urlConnection, url);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public interface SuccessLoadedJson {

        void onLoaded(JSONObject json);
    }
}
