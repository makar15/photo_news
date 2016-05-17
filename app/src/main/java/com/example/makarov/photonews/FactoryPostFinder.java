package com.example.makarov.photonews;

import com.example.makarov.photonews.models.Location;
import com.example.makarov.photonews.network.MediaPostParser;
import com.example.makarov.photonews.network.postfinders.PostFinderLocation;
import com.example.makarov.photonews.network.postfinders.PostFinderMediaPost;
import com.example.makarov.photonews.network.postfinders.PostFinderTag;
import com.octo.android.robospice.SpiceManager;

import javax.inject.Singleton;

@Singleton
public class FactoryPostFinder {

    private final SpiceManager mSpiceManager;
    private final MediaPostParser mMediaPostParser;

    public FactoryPostFinder(SpiceManager spiceManager, MediaPostParser mediaPostParser) {
        mSpiceManager = spiceManager;
        mMediaPostParser = mediaPostParser;
    }

    public PostFinderLocation getPostFinderLocation(Location location) {
        return new PostFinderLocation(location, mSpiceManager, mMediaPostParser);
    }

    public PostFinderTag getPostFinderTag(String lineTag) {
        return new PostFinderTag(lineTag, mSpiceManager, mMediaPostParser);
    }

    public PostFinderMediaPost getPostFinderMediaPost() {
        return new PostFinderMediaPost(mSpiceManager, mMediaPostParser);
    }
}
