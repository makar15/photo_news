package com.example.makarov.photonews.ui.fragments;

import com.example.makarov.photonews.network.PostFinder;

public class MediaPostFragment extends PhotoFragment {

    @Override
    protected PostFinder createPostFinder() {
        if (getArguments() == null) {
            return null;
        }

        if (!getArguments().containsKey(PhotoFragment.MEDIA_POST_RESULT_KEY)) {
            return null;
        }

        String temp = getArguments().getString(PhotoFragment.MEDIA_POST_RESULT_KEY);
        return mFactoryPostFinder.getPostFinderMediaPost();
    }

}
