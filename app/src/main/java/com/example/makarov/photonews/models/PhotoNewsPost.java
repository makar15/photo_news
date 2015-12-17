package com.example.makarov.photonews.models;

/**
 * Created by makarov on 16.12.15.
 *
 */
public class PhotoNewsPost {

    private String mAuthor;
    private String mUrlAddress;
    private int mCountLikes;

    public PhotoNewsPost(String author, String urlAddress, int countLikes) {
        mAuthor = author;
        mUrlAddress = urlAddress;
        mCountLikes = countLikes;
    }

    public void setCountLikes(int countLikes) {
        this.mCountLikes = countLikes;
    }

    public void setAuthor(String author) {
        this.mAuthor = author;
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
