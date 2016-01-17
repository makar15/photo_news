package com.example.makarov.photonews.ui.fragments;

import com.example.makarov.photonews.models.Location;
import com.example.makarov.photonews.network.PostFinder;
import com.example.makarov.photonews.network.PostFinderLocation;

public class PhotoLocationFragment extends PhotoFragment {

    @Override
    protected PostFinder createPostFinder() {
        if (getArguments() == null) {
            return null;
        }

        if (!getArguments().containsKey(GoogleMapFragment.GOOGLE_MAP_KEY)) {
            return null;
        }

        Location location = getArguments().getParcelable(GoogleMapFragment.GOOGLE_MAP_KEY);
        return new PostFinderLocation(location);
    }
}
