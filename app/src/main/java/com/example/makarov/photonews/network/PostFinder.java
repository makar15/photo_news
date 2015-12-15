package com.example.makarov.photonews.network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by makarov on 11.12.15.
 * start stream, generation URL, parsing JSON
 */
public class PostFinder {

    private final String PHOTO_STANDART_RESOLUTION = "standard_resolution";
    private final String PHOTO_LOW_RESOLUTION = "low_resolution";
    private final String PHOTO_THUMBNAIL = "thumbnail";

    private final String VERSION_API_URL = "https://api.instagram.com/v1";
    private final String ACCESS_TOKEN = "175770414.98d6195.7e44bb27685141c5ba834cb7dcd67625";
    private final int NUMBER_SINGLE_QUERY = 10;

    private final Parsing mParsing;
    private URL mUrlSavePhotosTag;
    private String mLineTag;

    private List<String> mUrlImages;

    public PostFinder(String lineTag) {
        mLineTag = lineTag;

        mParsing = new Parsing();
        mUrlImages = new ArrayList<>();
    }

    public void requestPhotosTag(final SuccessLoadedUrls successLoaded) {
        mUrlSavePhotosTag = getGenerationUrlAccessPhotosTag(mLineTag);
        new PostRequest(new PostRequest.SuccessLoadedJson() {
            @Override
            public void onLoaded(JSONObject json) {

                if (successLoaded != null) {
                    try {
                        successLoaded.onLoaded(parseJsonToList(json));
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).execute(mUrlSavePhotosTag);
    }

    public void nextRequestPhotosTag(final SuccessLoadedUrls successLoaded) {
        mUrlSavePhotosTag = getGenerationNextUrlAccessPhotosTag(mLineTag);
        new PostRequest(new PostRequest.SuccessLoadedJson() {
            @Override
            public void onLoaded(JSONObject json) {

                if (successLoaded != null) {
                    try {
                        successLoaded.onLoaded(parseJsonToList(json));
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).execute(mUrlSavePhotosTag);
    }

    //получить URL для загрузки изображений по тэгу
    private URL getGenerationUrlAccessPhotosTag(String lineTag) {

        String url = VERSION_API_URL + "/tags/" + lineTag +
                "/media/recent/?access_token=" + ACCESS_TOKEN;
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private URL getGenerationNextUrlAccessPhotosTag(String lineTag) {

        String nextUrl = VERSION_API_URL + "/tags/" + lineTag + "/media/recent/?access_token=" +
                ACCESS_TOKEN + "&max_tag_id=" + mParsing.getNextMaxId();
        try {
            return new URL(nextUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<String> parseJsonToList(JSONObject json) throws IOException, JSONException {

        try {
            int count = 0;
            JSONArray mJsonArray = mParsing.getJSONObjectToJSONArray(json);

            while (count != NUMBER_SINGLE_QUERY) {
                String mResultJson = mParsing.getUrlImage(mJsonArray, count, PHOTO_STANDART_RESOLUTION);

                mUrlImages.add(mResultJson);
                count++;
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return mUrlImages;
    }


    public interface SuccessLoadedUrls {

        void onLoaded(List<String> urlImages);
    }
}
