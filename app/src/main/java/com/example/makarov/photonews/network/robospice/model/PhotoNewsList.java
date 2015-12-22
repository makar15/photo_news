package com.example.makarov.photonews.network.robospice.model;

import com.example.makarov.photonews.models.PhotoNewsPost;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makarov on 22.12.15.
 */
public class PhotoNewsList {

    List<PhotoNewsPost> mPhotoNewsPosts;

    public PhotoNewsList() {
        mPhotoNewsPosts = new ArrayList<>();
    }

    public List<PhotoNewsPost> getPhotoNewsPosts() {
        return mPhotoNewsPosts;
    }

}
