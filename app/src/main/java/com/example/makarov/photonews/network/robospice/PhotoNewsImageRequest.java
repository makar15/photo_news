package com.example.makarov.photonews.network.robospice;

import com.example.makarov.photonews.network.NextPageUrlSaver;
import com.example.makarov.photonews.network.Parsing;
import com.example.makarov.photonews.network.robospice.model.PhotoNewsList;
import com.example.makarov.photonews.utils.JsonUtils;
import com.example.makarov.photonews.utils.StreamUtils;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class PhotoNewsImageRequest extends SpringAndroidSpiceRequest<PhotoNewsList> {

    private URL mUrl;
    private NextPageUrlSaver mNextPageUrlSaver;

    public PhotoNewsImageRequest(URL url, NextPageUrlSaver nextPageUrlSaver) {
        super(PhotoNewsList.class);

        mUrl = url;
        mNextPageUrlSaver = nextPageUrlSaver;
    }

    @Override
    public PhotoNewsList loadDataFromNetwork() throws JSONException, IOException {

        try {
            StreamUtils.openHttpUrlConnection(null, mUrl);
            String response = StreamUtils.urlToString(mUrl);
            JSONObject jsonObject = JsonUtils.getStringToJSONObject(response);

            saveNextUrl(jsonObject);
            return new Parsing().jsonToPhotoNews(jsonObject);
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

    public String createCacheKey() {
        return "URL: " + mUrl;
    }

}
