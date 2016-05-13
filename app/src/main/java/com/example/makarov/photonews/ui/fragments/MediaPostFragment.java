package com.example.makarov.photonews.ui.fragments;

import com.example.makarov.photonews.network.postfinders.PostFinder;

public class MediaPostFragment extends PhotoFragment {

    @Override
    protected PostFinder createPostFinder() {
        return mFactoryPostFinder.getPostFinderMediaPost();
    }

}
