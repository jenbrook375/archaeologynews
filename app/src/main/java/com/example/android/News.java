package com.example.android.archaeologynews;

public class News {
    // News is a news article from the Guardian

    // the title of the article
    private String mWebTitle;

    // the section of the Guardian that the article is in
    private String mSection;

    // the author of the article
    private String mWebUrl;

    // the date the article was published
    private String mDate;

    // the author of the article
    private String mAuthor;

    /**
     * Constructor for the News object
     *
     * @param title   is the title of the article
     * @param section is the section that the article is in
     * @param webUrl  is the url of the article
     * @param date    is the date of the article
     * @param author  is the name of the author
     */

    public News(String title, String section, String webUrl, String author, String date) {
        mWebTitle = title;
        mSection = section;
        mWebUrl = webUrl;
        mDate = date;
        mAuthor = author;
    }

    public String getTitle() {
        return mWebTitle;
    }

    public String getSection() {
        return mSection;
    }

    public String getWebUrl() {
        return mWebUrl;
    }

    public String getAuthor() {return mAuthor;}

    public String getDate() {
        return mDate;
    }

}