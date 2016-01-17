package com.example.makarov.photonews.network.robospice;

import com.example.makarov.photonews.models.Location;
import com.example.makarov.photonews.network.NextPageUrlSaver;
import com.example.makarov.photonews.network.Parsing;
import com.example.makarov.photonews.network.robospice.model.PhotoNewsList;
import com.example.makarov.photonews.utils.Constants;
import com.example.makarov.photonews.utils.JsonUtils;
import com.example.makarov.photonews.utils.StreamUtils;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PhotoNewsLocationRequest extends SpringAndroidSpiceRequest<PhotoNewsList> {

    private URL mUrl;
    private NextPageUrlSaver mNextPageUrlSaver;
    private Location mLocation;

    public PhotoNewsLocationRequest(URL url, NextPageUrlSaver nextPageUrlSaver, Location location) {
        super(PhotoNewsList.class);

        mUrl = url;
        mNextPageUrlSaver = nextPageUrlSaver;
        mLocation = location;
    }

    @Override
    public PhotoNewsList loadDataFromNetwork() {

        try {
            JSONObject jsonObject = getJson(mUrl);

            saveNextUrl(jsonObject);
            return new Parsing().jsonToPhotoNews(jsonObject);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveNextUrl(JSONObject jsonObject) throws IOException {
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            int countItem = jsonArray.length();

            if (countItem > 1) {
                JSONObject secondJsonObject = jsonArray.getJSONObject(1);
                String createdTimeSecondItem = secondJsonObject.getString("created_time");

                URL endUrl = getInterimUrl(createdTimeSecondItem);
                JSONObject endJson = getJson(endUrl);

                JSONArray endJsonArray = endJson.getJSONArray("data");
                int endCountItem = endJsonArray.length();

                if (endCountItem > 1) {
                    JSONObject lastJsonObject = endJsonArray.getJSONObject(endCountItem - 1);
                    mNextPageUrlSaver.setUrl(lastJsonObject.getString("created_time"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public URL getInterimUrl(String createdTime) {

        String nextUrl = Constants.VERSION_API_URL + "/media/search?" + "lat="
                + mLocation.getLatitude() + "&lng=" + mLocation.getLongitude() + "&distance=2500" +
                "&max_timestamp=" + createdTime + "&access_token=" + Constants.ACCESS_TOKEN;
        try {
            return new URL(nextUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private JSONObject getJson(URL url) throws IOException, JSONException {
        StreamUtils.openHttpUrlConnection(null, url);
        String response = StreamUtils.urlToString(url);
        JSONObject json = JsonUtils.getStringToJSONObject(response);
        return json;
    }

    public String createCacheKey() {
        return "URL: " + mUrl;
    }

}
