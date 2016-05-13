package com.example.makarov.photonews.network.savers;

public interface NextPageUrlSaver {

    void setUrl(String url);

    String getUrl();

    boolean isNextLoading();
}
