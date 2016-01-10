package com.example.makarov.photonews;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.example.makarov.photonews.database.TagDbAdapter;
import com.example.makarov.photonews.network.robospice.PhotoNewsSpiceService;
import com.octo.android.robospice.SpiceManager;

public class PhotoNewsApp extends MultiDexApplication {

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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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
