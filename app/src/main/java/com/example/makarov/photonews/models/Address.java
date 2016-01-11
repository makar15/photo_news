package com.example.makarov.photonews.models;

public class Address {

    private final double mLatitude;
    private final double mLongitude;

    public Address(double latitude, double longitude) {
        mLatitude = latitude;
        mLongitude = longitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public double getLatitude() {
        return mLatitude;
    }
}
