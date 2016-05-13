package com.example.makarov.photonews.network.savers;

import com.example.makarov.photonews.models.Location;
import com.example.makarov.photonews.utils.UrlInstaUtils;

public class NextPageUrlSaverLocation implements NextPageUrlSaver {

    private final Location mLocation;

    private String mCreatedTime;
    private boolean mIsNextLoading;

    public NextPageUrlSaverLocation(Location location) {
        mLocation = location;
    }

    public void setUrl(String createdTime) {
        mCreatedTime = createdTime;
        mIsNextLoading = true;
    }

    public String getUrl() {
        mIsNextLoading = false;
        return UrlInstaUtils.getNextUrlPhotosLocation(mLocation, mCreatedTime);
    }

    public boolean isNextLoading() {
        return mIsNextLoading;
    }
}
