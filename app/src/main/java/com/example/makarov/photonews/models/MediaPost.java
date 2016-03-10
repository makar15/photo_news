package com.example.makarov.photonews.models;

public class MediaPost {

    private final String mId;
    private final String mUrlAddress;

    private String mAuthor;
    private int mCountLikes;

    public MediaPost(String id, String author, String urlAddress, int countLikes) {
        mId = id;
        mUrlAddress = urlAddress;
        mAuthor = author;
        mCountLikes = countLikes;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public void setCountLikes(int countLikes) {
        mCountLikes = countLikes;
    }

    public String getId() {
        return mId;
    }

    public String getUrlAddress() {
        return mUrlAddress;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public int getCountLikes() {
        return mCountLikes;
    }

}
