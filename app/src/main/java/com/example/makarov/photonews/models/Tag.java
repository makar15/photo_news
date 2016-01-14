package com.example.makarov.photonews.models;

public class Tag implements Subscription {

    private final String mName;
    private final long mDate;

    public Tag(String name, long date) {
        mName = name;
        mDate = date;
    }

    public String getName() {
        return mName;
    }

    public long getDate() {
        return mDate;
    }
}
