package com.example.makarov.photonews;

import com.example.makarov.photonews.models.Location;
import com.example.makarov.photonews.network.PostFinderLocation;
import com.example.makarov.photonews.network.PostFinderTag;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FactoryPostFinder {

    @Inject
    public PostFinderLocation getPostFinderLocation(Location location) {
        return new PostFinderLocation(location);
    }

    @Inject
    public PostFinderTag getPostFinderTag(String lineTag) {
        return new PostFinderTag(lineTag);
    }
}
