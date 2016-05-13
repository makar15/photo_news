package com.example.makarov.photonews.network.robospice.requests;

import com.example.makarov.photonews.models.Location;
import com.example.makarov.photonews.network.Parsing;
import com.example.makarov.photonews.network.okhttp.API;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;
import com.example.makarov.photonews.network.savers.NextPageUrlSaver;
import com.example.makarov.photonews.utils.UrlInstaUtils;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

public class LocationRequest extends SpringAndroidSpiceRequest<MediaPostList> {

    private final String mUrl;
    private final NextPageUrlSaver mUrlSaver;
    private final Location mLocation;

    public LocationRequest(String url, NextPageUrlSaver urlSaver, Location location) {
        super(MediaPostList.class);
        mUrl = url;
        mUrlSaver = urlSaver;
        mLocation = location;
    }

    @Override
    public MediaPostList loadDataFromNetwork() throws IOException, JSONException {
        JSONObject jsonObject = getJson(mUrl);
        saveNextUrl(jsonObject);

        return Parsing.jsonToMediaPosts(jsonObject);
    }

    private void saveNextUrl(JSONObject jsonObject) throws IOException, JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        int countItem = jsonArray.length();

        if (countItem <= 1) {
            return;
        }
        JSONObject secondJsonObject = jsonArray.getJSONObject(1);
        String createdTimeSecondItem = secondJsonObject.getString("created_time");

        String endUrl = UrlInstaUtils.getNextUrlPhotosLocation(mLocation, createdTimeSecondItem);
        JSONObject endJson = getJson(endUrl);

        JSONArray endJsonArray = endJson.getJSONArray("data");
        int endCountItem = endJsonArray.length();

        if (endCountItem > 1) {
            JSONObject lastJsonObject = endJsonArray.getJSONObject(endCountItem - 1);
            mUrlSaver.setUrl(lastJsonObject.getString("created_time"));
        }
    }

    private JSONObject getJson(String url) throws IOException, JSONException {
        String response = API.getResponse(url);
        return (JSONObject) new JSONTokener(response).nextValue();
    }
}
