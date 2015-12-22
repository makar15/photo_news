package com.example.makarov.photonews;

import android.app.Application;

import com.example.makarov.photonews.database.TagDbAdapter;
import com.example.makarov.photonews.network.robospice.PhotoNewsSpiceService;
import com.octo.android.robospice.SpiceManager;

/**
 * Created by makarov on 11.12.15.
 */
public class PhotoNewsApp extends Application {

    private static PhotoNewsApp app;
    private TagDbAdapter mTagDbAdapter;
    private SpiceManager mSpiceManager;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
        mTagDbAdapter = new TagDbAdapter(this);
        mSpiceManager = new SpiceManager(PhotoNewsSpiceService.class);
        mSpiceManager.start(this);
    }

    public static PhotoNewsApp getApp() {
        return app;
    }

    public TagDbAdapter getTagDbAdapter() {
        return mTagDbAdapter;
    }

    public SpiceManager getSpiceManager() {
        return mSpiceManager;
    }

}
