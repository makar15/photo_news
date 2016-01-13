package com.example.makarov.photonews.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {

    private final double mLatitude;
    private final double mLongitude;
    private final String mCountryName;
    private final String mLocality;
    private final String mThoroughfare;

    public Address(double latitude, double longitude, String countryName, String locality, String thoroughfare) {
        mLatitude = latitude;
        mLongitude = longitude;
        mCountryName = countryName;
        mLocality = locality;
        mThoroughfare = thoroughfare;
    }

    protected Address(Parcel in) {
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
        mCountryName = in.readString();
        mLocality = in.readString();
        mThoroughfare = in.readString();
    }

    public static final Creator<Address> CREATOR = new Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };

    public double getLongitude() {
        return mLongitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public String getCountryName() {
        return mCountryName;
    }

    public String getLocality() {
        return mLocality;
    }

    public String getThoroughfare() {
        return mThoroughfare;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLongitude);
        dest.writeString(mCountryName);
        dest.writeString(mLocality);
        dest.writeString(mThoroughfare);
    }
}
