package com.example.makarov.photonews.ui.fragments;

import com.example.makarov.photonews.network.PostFinder;

public class PhotoNewsFragment extends PhotoFragment {

    @Override
    protected PostFinder createPostFinder() {
        if (getArguments() == null) {
            return null;
        }

        if (!getArguments().containsKey(PhotoFragment.PHOTO_RESULT_TAG_KEY)) {
            return null;
        }

        String temp = getArguments().getString(PhotoFragment.PHOTO_RESULT_TAG_KEY);
        return mFactoryPostFinder.getPostFinderPhotoNews();
    }

}
