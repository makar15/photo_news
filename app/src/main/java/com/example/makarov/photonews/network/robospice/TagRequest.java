package com.example.makarov.photonews.network.robospice;

import com.example.makarov.photonews.network.NextPageUrlSaver;
import com.example.makarov.photonews.network.Parsing;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;
import com.example.makarov.photonews.utils.JsonUtils;
import com.example.makarov.photonews.utils.StreamUtils;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class TagRequest extends SpringAndroidSpiceRequest<MediaPostList> {

    private final URL mUrl;
    private final NextPageUrlSaver mNextPageUrlSaver;

    public TagRequest(URL url, NextPageUrlSaver nextPageUrlSaver) {
        super(MediaPostList.class);

        mUrl = url;
        mNextPageUrlSaver = nextPageUrlSaver;
    }

    @Override
    public MediaPostList loadDataFromNetwork() throws JSONException, IOException {

        try {
            StreamUtils.openHttpUrlConnection(null, mUrl);
            String response = StreamUtils.urlToString(mUrl);
            JSONObject jsonObject = JsonUtils.getStringToJSONObject(response);

            saveNextUrl(jsonObject);
            return Parsing.jsonToMediaPosts(jsonObject);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveNextUrl(JSONObject jsonObject) throws JSONException, IOException {
        JSONObject json = jsonObject.getJSONObject("pagination");
        if (json.has("next_url")) {
            String nextUrl = json.getString("next_url");
            mNextPageUrlSaver.setUrl(nextUrl);
        }
    }
}
