package com.example.makarov.photonews.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable, Subscription {

    private final double mLatitude;
    private final double mLongitude;
    private String mNameLocation;
    private final String mCountryName;
    private final String mLocality;
    private final String mThoroughfare;
    private final long mDate;

    public Address(String nameLocation, double latitude, double longitude, String countryName,
                   String locality, String thoroughfare, long date) {
        mNameLocation = nameLocation;
        mLatitude = latitude;
        mLongitude = longitude;
        mCountryName = countryName;
        mLocality = locality;
        mThoroughfare = thoroughfare;
        mDate = date;
    }

    protected Address(Parcel in) {
        mLatitude = in.readDouble();
        mLongitude = in.readDouble();
        mNameLocation = in.readString();
        mCountryName = in.readString();
        mLocality = in.readString();
        mThoroughfare = in.readString();
        mDate = in.readLong();
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

    public void setNameLocation(String nameLocation) {
        mNameLocation = nameLocation;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public String getNameLocation() {
        return mNameLocation;
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

    public long getDate() {
        return mDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mLatitude);
        dest.writeDouble(mLongitude);
        dest.writeString(mNameLocation);
        dest.writeString(mCountryName);
        dest.writeString(mLocality);
        dest.writeString(mThoroughfare);
        dest.writeLong(mDate);
    }
}
