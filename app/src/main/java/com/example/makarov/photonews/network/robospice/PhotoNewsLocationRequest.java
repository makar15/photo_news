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

public class PhotoNewsLocationRequest extends SpringAndroidSpiceRequest<PhotoNewsList> {

    private URL mUrl;
    private NextPageUrlSaver mNextPageUrlSaver;

    public PhotoNewsLocationRequest(URL url, NextPageUrlSaver nextPageUrlSaver) {
        super(PhotoNewsList.class);

        mUrl = url;
        mNextPageUrlSaver = nextPageUrlSaver;
    }

    @Override
    public PhotoNewsList loadDataFromNetwork() throws Exception {

        try {
            StreamUtils.openHttpUrlConnection(null, mUrl);
            String response = StreamUtils.urlToString(mUrl);
            JSONObject jsonObject = JsonUtils.getStringToJSONObject(response);
            //saveNextUrl(jsonObject);

            return new Parsing().jsonToPhotoNews(jsonObject);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveNextUrlPhotos(JSONObject jsonObject) {
        try {
            JSONObject nextUrl = (JSONObject) jsonObject.get("pagination");
            mNextPageUrlSaver.setUrl((String) nextUrl.get("next_max_tag_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String createCacheKey() {
        return "URL: " + mUrl;
    }

}
