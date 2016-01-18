package com.example.makarov.photonews.ui.fragments;

import com.example.makarov.photonews.FactoryPostFinder;
import com.example.makarov.photonews.models.Location;
import com.example.makarov.photonews.network.PostFinder;

public class PhotoLocationFragment extends PhotoFragment {

    @Override
    protected PostFinder createPostFinder(FactoryPostFinder factoryPostFinder) {
        if (getArguments() == null) {
            return null;
        }

        if (!getArguments().containsKey(GoogleMapFragment.GOOGLE_MAP_KEY)) {
            return null;
        }

        Location location = getArguments().getParcelable(GoogleMapFragment.GOOGLE_MAP_KEY);
        return factoryPostFinder.getPostFinderLocation(location);
    }
}
