package com.example.makarov.photonews.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.makarov.photonews.models.Location;
import com.example.makarov.photonews.network.postfinders.PostFinder;

public class PhotoLocationFragment extends PhotoFragment {

    @Nullable
    @Override
    protected PostFinder createPostFinder() {
        Bundle args = getArguments();
        if (args == null || !getArguments().containsKey(PhotoFragment.LOCATION_KEY)) {
            return null;
        }

        Location location = args.getParcelable(PhotoFragment.LOCATION_KEY);
        return mFactoryPostFinder.getPostFinderLocation(location);
    }
}
