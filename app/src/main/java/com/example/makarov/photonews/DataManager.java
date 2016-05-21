package com.example.makarov.photonews;

import com.example.makarov.photonews.models.MediaPost;
import com.example.makarov.photonews.models.Subscription;
import com.example.makarov.photonews.network.postfinders.PostFinder;

import java.util.List;

import javax.inject.Inject;

public class DataManager {

    public static final String LOCATION_REQUEST_TYPE = "location";
    public static final String TAG_REQUEST_TYPE = "tag";
    public static final String MEDIA_POST_REQUEST_TYPE = "media_post";

    @Inject
    FactoryPostFinder mFactoryPostFinder;

    public interface OnLoadMediaPostCallback {

        void onLoadFailedMediaPosts(Exception e);

        void onLoadSuccessMediaPosts(List<MediaPost> list);
    }

    public DataManager() {

    }

    /**
     *
     */
    private PostFinder createPostFinder(String requestType, Subscription subscription) {
        switch (requestType){
            case LOCATION_REQUEST_TYPE:

                break;

            case TAG_REQUEST_TYPE:
                return mFactoryPostFinder.getPostFinderTag(subscription.getName());

            case MEDIA_POST_REQUEST_TYPE:

                break;
            default: return null;
        }
        return null;
    }
}
