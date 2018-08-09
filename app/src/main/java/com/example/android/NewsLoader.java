package com.example.android.archaeologynews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {


    private static final String LOG_TAG = NewsLoader.class.getName();

    // set query variable
    private String mUrl;


    /**
     * newsLoader constructor
     *
     * @param context is the context of the activity
     * @param url     is the url to query
     */
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    // starts loading when activity is started
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    // Loads data in background
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<News> news = QueryUtils.fetchNewsData(mUrl);
        return news;
    }
}
