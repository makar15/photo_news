package com.example.makarov.photonews;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.example.makarov.photonews.di.AppInjector;
import com.example.makarov.photonews.network.robospice.SpiceService;
import com.octo.android.robospice.SpiceManager;

public class PhotoNewsApp extends MultiDexApplication {

    private static PhotoNewsApp mApp;
    private SpiceManager mSpiceManager;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;

        AppInjector.buildComponent(this);
        mSpiceManager = new SpiceManager(SpiceService.class);
        mSpiceManager.start(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public static PhotoNewsApp getApp() {
        return mApp;
    }

    public SpiceManager getSpiceManager() {
        return mSpiceManager;
    }

}
