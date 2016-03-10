package com.example.makarov.photonews.network.robospice.model;

import com.example.makarov.photonews.models.MediaPost;

import java.util.ArrayList;
import java.util.List;

public class MediaPostList {

    List<MediaPost> mMediaPosts;

    public MediaPostList() {
        mMediaPosts = new ArrayList<>();
    }

    public List<MediaPost> getMediaPosts() {
        return mMediaPosts;
    }

}
