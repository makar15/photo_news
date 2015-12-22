package com.example.makarov.photonews.network.robospice;

import com.example.makarov.photonews.models.PhotoNewsPost;
import com.example.makarov.photonews.network.Parsing;
import com.example.makarov.photonews.network.UrlToJsonObject;
import com.example.makarov.photonews.network.robospice.model.PhotoNewsList;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

/**
 * Created by makarov on 21.12.15.
 */
public class PhotoNewsSpiceRequest extends SpringAndroidSpiceRequest<PhotoNewsList> {

    private URL mUrl;
    private JSONObject jsonObject;
    private Parsing mParsing;

    public PhotoNewsSpiceRequest(URL url, Parsing parsing) {
        super(PhotoNewsList.class);

        mUrl = url;
        mParsing = parsing;
    }

    @Override
    public PhotoNewsList loadDataFromNetwork() throws Exception {

        //getRestTemplate().getForObject(mUrl.toURI(), JSONObject.class);
        return parseJsonToList(getJson(mUrl));
    }

    private JSONObject getJson(URL url) throws IOException, JSONException {

        UrlToJsonObject mUrlToJsonObject = new UrlToJsonObject();

        try {
            String response = mUrlToJsonObject.convertString(url);
            //TODO verification meta code 200 || jsonObject = null
            jsonObject = mUrlToJsonObject.getStringToJSONObject(response);
            mUrlToJsonObject.openHttpUrlConnection(null, url);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private PhotoNewsList parseJsonToList(JSONObject json) throws IOException, JSONException {

        PhotoNewsList photoNews = new PhotoNewsList();

        try {
            JSONArray mJsonArray = mParsing.getJSONObjectToJSONArray(json);
            int NUMBER_SINGLE_QUERY = 20;
            int count = 0;

            while (count != NUMBER_SINGLE_QUERY) {
                String urlImage = mParsing.getUrlImage(mJsonArray, count);
                String author = mParsing.getAuthor(mJsonArray, count);
                int countLikes = mParsing.getCountLikes(mJsonArray, count);

                PhotoNewsPost photoNewsPost = new PhotoNewsPost(author, urlImage, countLikes);
                photoNews.getPhotoNewsPosts().add(photoNewsPost);

                count++;
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return photoNews;
    }

    public String createCacheKey() {
        return "URL: " + mUrl;
    }

}
