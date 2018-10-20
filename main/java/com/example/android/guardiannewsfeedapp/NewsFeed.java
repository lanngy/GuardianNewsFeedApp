package com.example.android.guardiannewsfeedapp;

public class NewsFeed {
    private long mdate;
    private String mwebTitle;
    private String mwebUrl;
    private String mauthor;

    public NewsFeed ( long date, String webTitle, String webUrl, String author) {
        mdate=date;
        mwebTitle=webTitle;
        mwebUrl=webUrl;
        mauthor=author;
    }
    public long getDate() {
        return mdate;
    }
    public String getWebTitle() {
        return mwebTitle;
    }
    public String getWebUrl() {
        return mwebUrl;
    }
    public String getAuthor() { return mauthor; }
}

