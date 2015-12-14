package com.example.makarov.photonews;

import android.app.Application;

import com.example.makarov.photonews.network.Parsing;
import com.example.makarov.photonews.network.RequestService;

/**
 * Created by makarov on 11.12.15.
 */
public class MyApp extends Application {

    private static MyApp app;

    private RequestService requestService;
    private Parsing parsing;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
        requestService = new RequestService();
        parsing = new Parsing();
    }

    public static MyApp getApp() {
        return app;
    }

    public RequestService getRequestService() {
        return requestService;
    }

    public Parsing getParsing() {
        return parsing;
    }
}
