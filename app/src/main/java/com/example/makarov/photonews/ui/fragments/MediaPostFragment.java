package com.example.makarov.photonews.ui.fragments;

import android.support.annotation.Nullable;

import com.example.makarov.photonews.network.postfinders.PostFinder;

public class MediaPostFragment extends PhotoFragment {

    @Nullable
    @Override
    protected PostFinder createPostFinder() {
        return mFactoryPostFinder.getPostFinderMediaPost();
    }
}
