package com.example.android.latestnews;

import android.graphics.Bitmap;

/**
 * Created by vanshika on 5/6/17.
 * <p>
 * Creates an {@link News} object.
 */
public class News {

    //The title of the news story
    private String mNewsHeading;

    //The description of the news item.
    private String mNewsDescription;

    //The link to the Url of the full story.
    private String mNewsUrl;


    /**
     * Creates an {@link News} object.
     *
     * @param newsHeading is the title of the news story
     * @param newsDescription is the description of the news item
     * @param newsUrl is the link to the Url of the full story.
     */
    public News(String newsHeading, String newsDescription, String newsUrl) {
        mNewsHeading = newsHeading;
        mNewsDescription = newsDescription;
        mNewsUrl = newsUrl;
    }

    /**
     * @return the news heading
     */
    public String getNewsHeading() {
        return mNewsHeading;
    }

    /**
     * @return the news description
     */
    public String getNewsDescription() {
        return mNewsDescription;
    }

    /**
     * @return the news url.
     */
    public String getNewsUrl() {
        return mNewsUrl;
    }

}
