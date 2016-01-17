package com.example.makarov.photonews.network;

import java.net.URL;

public interface NextPageUrlSaver {

    void setUrl(String url);

    URL getUrl();

    boolean getNextLoading();
}
