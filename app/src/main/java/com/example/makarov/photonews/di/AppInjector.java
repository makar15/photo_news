package com.example.makarov.photonews.di;

import android.content.Context;

public class AppInjector {

    private static AppComponent mAppComponent;

    public static void buildComponent(Context context) {
        mAppComponent = DaggerAppComponent.builder()
                .mainModule(new MainModule(context))
                .build();
    }

    public static AppComponent get() {
        return mAppComponent;
    }
}
