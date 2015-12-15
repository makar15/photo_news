package com.example.makarov.photonews;

import android.app.Application;

/**
 * Created by makarov on 11.12.15.
 */
public class InstagramApp extends Application {

    private static InstagramApp app;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
    }

    public static InstagramApp getApp() {
        return app;
    }

}
