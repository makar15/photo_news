package com.example.makarov.photonews.models;

public class MediaPost {

    private final String mId;
    private final String mUrlAddress;

    private String mAuthor;
    private String mProfilePicture;
    private int mCountLikes;

    public MediaPost(String id, String author, String profilePicture, String urlAddress, int countLikes) {
        mId = id;
        mUrlAddress = urlAddress;
        mAuthor = author;
        mProfilePicture = profilePicture;
        mCountLikes = countLikes;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public void setProfilePicture(String profilePicture) {
        mProfilePicture = profilePicture;
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

    public String getProfilePicture() {
        return mProfilePicture;
    }

    public int getCountLikes() {
        return mCountLikes;
    }

}
