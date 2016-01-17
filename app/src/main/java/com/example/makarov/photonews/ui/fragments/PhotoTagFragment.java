package com.example.makarov.photonews.ui.fragments;

import com.example.makarov.photonews.network.PostFinder;
import com.example.makarov.photonews.network.PostFinderTag;

public class PhotoTagFragment extends PhotoFragment {

    @Override
    protected PostFinder createPostFinder() {
        if (getArguments() == null) {
            return null;
        }

        if (!getArguments().containsKey(PhotoFragment.PHOTO_RESULT_TAG_KEY)) {
            return null;
        }

        String tagName = getArguments().getString(PhotoFragment.PHOTO_RESULT_TAG_KEY);
        return new PostFinderTag(tagName);
    }

}
