package com.example.makarov.photonews;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.example.makarov.photonews.di.AppInjector;

public class PhotoNewsApp extends MultiDexApplication {

    private static PhotoNewsApp mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        AppInjector.buildComponent(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static PhotoNewsApp getApp() {
        return mApp;
    }
}
