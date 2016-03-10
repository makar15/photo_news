package com.example.makarov.photonews.ui.fragments;

import com.example.makarov.photonews.models.Location;
import com.example.makarov.photonews.network.PostFinder;

public class PhotoLocationFragment extends PhotoFragment {

    @Override
    protected PostFinder createPostFinder() {
        if (getArguments() == null) {
            return null;
        }

        if (!getArguments().containsKey(PhotoFragment.PHOTO_RESULT_LOCATION_KEY)) {
            return null;
        }

        Location location = getArguments().getParcelable(PhotoFragment.PHOTO_RESULT_LOCATION_KEY);
        return mFactoryPostFinder.getPostFinderLocation(location);
    }
}
