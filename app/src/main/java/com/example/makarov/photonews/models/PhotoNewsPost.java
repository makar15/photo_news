package com.example.makarov.photonews.models;

public class PhotoNewsPost {

    private String mAuthor;
    private String mUrlAddress;
    private int mCountLikes;

    public PhotoNewsPost(String author, String urlAddress, int countLikes) {
        mAuthor = author;
        mUrlAddress = urlAddress;
        mCountLikes = countLikes;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getUrlAddress() {
        return mUrlAddress;
    }

    public int getCountLikes() {
        return mCountLikes;
    }

}
