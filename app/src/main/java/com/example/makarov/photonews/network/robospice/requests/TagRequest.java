package com.example.makarov.photonews.network.robospice.requests;

import com.example.makarov.photonews.network.MediaPostParser;
import com.example.makarov.photonews.network.okhttp.API;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;
import com.example.makarov.photonews.network.savers.NextPageUrlSaver;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;

public class TagRequest extends SpringAndroidSpiceRequest<MediaPostList> {

    private final MediaPostParser mMediaPostParser;
    private final String mUrl;
    private final NextPageUrlSaver mUrlSaver;

    public TagRequest(MediaPostParser mediaPostParser, String url, NextPageUrlSaver urlSaver) {
        super(MediaPostList.class);

        mMediaPostParser = mediaPostParser;
        mUrl = url;
        mUrlSaver = urlSaver;
    }

    @Override
    public MediaPostList loadDataFromNetwork() throws IOException, JSONException {
        String response = API.getResponse(mUrl);
        JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();

        //save next url
        JSONObject json = jsonObject.getJSONObject("pagination");
        if (json.has("next_url")) {
            String nextUrl = json.getString("next_url");
            mUrlSaver.setUrl(nextUrl);
        }

        return mMediaPostParser.parse(jsonObject);
    }
}
