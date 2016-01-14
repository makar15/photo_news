package com.example.makarov.photonews.models;

public class Tag implements Subscription {

    private final String mNameTag;
    private final long mDate;

    public Tag(String nameTag, long date) {
        mNameTag = nameTag;
        mDate = date;
    }

    public String getNameTag() {
        return mNameTag;
    }

    public long getDate() {
        return mDate;
    }
}
