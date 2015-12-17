package com.example.makarov.photonews;

import android.app.Application;

import com.example.makarov.photonews.database.TagDbAdapter;

/**
 * Created by makarov on 11.12.15.
 */
public class PhotoNewsApp extends Application {

    private static PhotoNewsApp app;
    private TagDbAdapter mTagDbAdapter;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
        mTagDbAdapter = new TagDbAdapter(this);
    }

    public static PhotoNewsApp getApp() {
        return app;
    }

    public TagDbAdapter getTagDbAdapter() {
        return mTagDbAdapter;
    }

}
