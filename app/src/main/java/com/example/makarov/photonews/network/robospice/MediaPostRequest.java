package com.example.makarov.photonews.network.robospice;

import com.example.makarov.photonews.database.MediaPostDbAdapter;
import com.example.makarov.photonews.di.AppInjector;
import com.example.makarov.photonews.models.MediaPost;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;

import com.example.makarov.photonews.utils.JsonUtils;
import com.example.makarov.photonews.utils.StreamUtils;
import com.example.makarov.photonews.utils.UrlInstaUtils;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MediaPostRequest extends SpringAndroidSpiceRequest<MediaPostList> {

    public MediaPostRequest() {
        super(MediaPostList.class);
    }

    @Override
    public MediaPostList loadDataFromNetwork() {

        MediaPostDbAdapter mediaPostDbAdapter = AppInjector.get().getMediaPostDbAdapter();
        List<MediaPost> posts = mediaPostDbAdapter.open().getAllMediaPosts();
        mediaPostDbAdapter.close();
        MediaPostList mediaPosts = new MediaPostList();
        List<MediaPost> newPosts = mediaPosts.getMediaPosts();

        for (int i = 0; i < posts.size(); i++) {
            MediaPost mediaPost = posts.get(i);
            JSONObject jsonObject = idPostToJson(mediaPost.getId());

            try {
                newPosts.add(AppInjector.get().getParsing().jsonToUpdateMediaPost(jsonObject));

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
        return mediaPosts;
    }

    private JSONObject idPostToJson(String idPost) {
        URL urlMediaPost = UrlInstaUtils.getUrlMediaPost(idPost);

        try {
            StreamUtils.openHttpUrlConnection(null, urlMediaPost);
            String response = StreamUtils.urlToString(urlMediaPost);
            return JsonUtils.getStringToJSONObject(response);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String createCacheKey() {
        return "URL: " ;
    }
}
