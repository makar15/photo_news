package com.example.makarov.photonews.network.robospice;

import com.example.makarov.photonews.database.MediaPostDbAdapter;
import com.example.makarov.photonews.di.AppInjector;
import com.example.makarov.photonews.models.MediaPost;
import com.example.makarov.photonews.network.Parsing;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;
import com.example.makarov.photonews.utils.JsonUtils;
import com.example.makarov.photonews.utils.StreamUtils;
import com.example.makarov.photonews.utils.UrlInstaUtils;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MediaPostRequest extends SpringAndroidSpiceRequest<MediaPostList> {

    private final int mNumberRequest;

    public MediaPostRequest(int numberRequest) {
        super(MediaPostList.class);

        mNumberRequest = numberRequest;
    }

    @Override
    public MediaPostList loadDataFromNetwork() {

        MediaPostDbAdapter mediaPostDbAdapter = AppInjector.get().getMediaPostDbAdapter();
        List<MediaPost> posts = mediaPostDbAdapter.open()
                .getLimitMediaPosts(mNumberRequest * MediaPostDbAdapter.QUERY_LIMIT);

        List<Integer> positionsFakeMediaPosts = new ArrayList<>();

        for (int i = 0; i < posts.size(); i++) {

            JSONObject jsonObject = idPostToJson(posts.get(i).getId());

            if (jsonObject != null) {
                try {
                    Parsing.jsonToUpdateMediaPost(posts.get(i), jsonObject);
                    mediaPostDbAdapter.update(posts.get(i));
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            } else {
                positionsFakeMediaPosts.add(i);
            }
        }

        if (positionsFakeMediaPosts.size() != 0) {
            for (Integer position : positionsFakeMediaPosts) {
                mediaPostDbAdapter.delete(posts.get(position));
                posts.remove(position.intValue());
            }
        }

        MediaPostList mediaPosts = new MediaPostList();
        mediaPosts.getMediaPosts().addAll(posts);
        mediaPostDbAdapter.close();

        return mediaPosts;
    }

    private JSONObject idPostToJson(String idPost) {
        URL urlMediaPost = UrlInstaUtils.getUrlMediaPost(idPost);

        try {
            if (StreamUtils.openHttpUrlConnection(null, urlMediaPost)) {
                String response = StreamUtils.urlToString(urlMediaPost);
                return JsonUtils.getStringToJSONObject(response);
            }
            return null;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
