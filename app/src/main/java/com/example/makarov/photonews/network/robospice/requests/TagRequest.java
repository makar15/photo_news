package com.example.makarov.photonews.network.robospice.requests;

import com.example.makarov.photonews.network.savers.NextPageUrlSaver;
import com.example.makarov.photonews.network.Parsing;
import com.example.makarov.photonews.network.okhttp.API;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

public class TagRequest extends SpringAndroidSpiceRequest<MediaPostList> {

    private final String mUrl;
    private final NextPageUrlSaver mUrlSaver;

    public TagRequest(String url, NextPageUrlSaver urlSaver) {
        super(MediaPostList.class);
        mUrl = url;
        mUrlSaver = urlSaver;
    }

    @Override
    public MediaPostList loadDataFromNetwork() throws IOException, JSONException {
        String response = API.getResponse(mUrl);
        JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();
        saveNextUrl(jsonObject);

        return Parsing.jsonToMediaPosts(jsonObject);
    }

    private void saveNextUrl(JSONObject jsonObject) throws JSONException {
        JSONObject json = jsonObject.getJSONObject("pagination");
        if (json.has("next_url")) {
            String nextUrl = json.getString("next_url");
            mUrlSaver.setUrl(nextUrl);
        }
    }
}
