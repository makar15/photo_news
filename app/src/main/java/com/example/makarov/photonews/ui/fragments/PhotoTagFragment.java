package com.example.makarov.photonews.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.makarov.photonews.network.postfinders.PostFinder;

public class PhotoTagFragment extends PhotoFragment {

    @Nullable
    @Override
    protected PostFinder createPostFinder() {
        Bundle args = getArguments();
        if (args == null || !args.containsKey(PhotoFragment.PHOTO_RESULT_TAG_KEY)) {
            return null;
        }

        String tagName = args.getString(PhotoFragment.PHOTO_RESULT_TAG_KEY);
        return mFactoryPostFinder.getPostFinderTag(tagName);
    }
}
