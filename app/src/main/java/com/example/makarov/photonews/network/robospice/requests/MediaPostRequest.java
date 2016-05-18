package com.example.makarov.photonews.network.robospice.requests;

import com.example.makarov.photonews.database.MediaPostDbAdapter;
import com.example.makarov.photonews.models.MediaPost;
import com.example.makarov.photonews.network.MediaPostParser;
import com.example.makarov.photonews.network.okhttp.API;
import com.example.makarov.photonews.network.robospice.model.MediaPostList;
import com.example.makarov.photonews.utils.UrlInstaUtils;
import com.octo.android.robospice.request.springandroid.SpringAndroidSpiceRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MediaPostRequest extends SpringAndroidSpiceRequest<MediaPostList> {

    private final MediaPostParser mMediaPostParser;
    private final MediaPostDbAdapter mMediaPostDbAdapter;
    private final int mNumberRequest;

    public MediaPostRequest(MediaPostParser mediaPostParser, MediaPostDbAdapter mediaPostDbAdapter,
                            int numberRequest) {
        super(MediaPostList.class);

        mMediaPostParser = mediaPostParser;
        mMediaPostDbAdapter = mediaPostDbAdapter;
        mNumberRequest = numberRequest;
    }

    @Override
    public MediaPostList loadDataFromNetwork() throws IOException, JSONException {

        List<MediaPost> posts = mMediaPostDbAdapter.open()
                .getLimitMediaPosts(mNumberRequest * MediaPostDbAdapter.QUERY_LIMIT);

        List<Integer> positionsFakeMediaPosts = new ArrayList<>();

        for (int i = 0; i < posts.size(); i++) {

            String urlMediaPost = UrlInstaUtils.getUrlMediaPost(posts.get(i).getId());
            String response = API.getResponse(urlMediaPost);
            JSONObject jsonObject = (JSONObject) new JSONTokener(response).nextValue();

            if (jsonObject != null) {
                mMediaPostParser.update(posts.get(i), jsonObject);
                mMediaPostDbAdapter.update(posts.get(i));
            } else {
                positionsFakeMediaPosts.add(i);
            }
        }

        if (positionsFakeMediaPosts.size() != 0) {
            for (Integer position : positionsFakeMediaPosts) {
                mMediaPostDbAdapter.delete(posts.get(position));
                posts.remove(position.intValue());
            }
        }

        MediaPostList mediaPosts = new MediaPostList();
        mediaPosts.getMediaPosts().addAll(posts);
        mMediaPostDbAdapter.close();

        return mediaPosts;
    }
}
