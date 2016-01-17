package com.example.makarov.photonews.network;

import com.example.makarov.photonews.models.Location;
import com.example.makarov.photonews.utils.Constants;

import java.net.MalformedURLException;
import java.net.URL;

public class NextPageUrlSaverLocation implements NextPageUrlSaver {

    private String mCreatedTime;
    private Location mLocation;

    public NextPageUrlSaverLocation(Location location) {
        mLocation = location;
    }

    public void setUrl(String createdTime) {
        mCreatedTime = createdTime;
    }

    public URL getUrl() {

        String nextUrl = Constants.VERSION_API_URL + "/media/search?" + "lat="
                + mLocation.getLatitude() + "&lng=" + mLocation.getLongitude() + "&distance=2500" +
                "&max_timestamp=" + mCreatedTime + "&access_token=" + Constants.ACCESS_TOKEN;
        try {
            return new URL(nextUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
