package com.example.android.archaeologynews;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    String author;

    private QueryUtils() {

    }

    /**
     * Query Guardian API
     */
    public static List<News> fetchNewsData(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        // Extract relevant fields from the JSON response and create a list of news items
        List<News> news = extractFeatureFromJson(jsonResponse);

        // Return the list of news items
        return news;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving Archaeology News JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> extractFeatureFromJson(String newsJson) {
        // quit method if json is empty or null
        if (TextUtils.isEmpty(newsJson)) {
            return null;
        }
        List<News> news = new ArrayList<>();

        try {

            // JsonObject created from Json string
            JSONObject rootObject = new JSONObject(newsJson);

            // get jsonObject with key name "response"
            JSONObject response = rootObject.getJSONObject("response");

            // get jsonArray with key name "results"
            JSONArray resultsArray = response.getJSONArray("results");

            // initialize variable i as position in array, then increment it as long as there
            // are fewer news items than the length of the array
            for (int i = 0; i < resultsArray.length(); i++) {

                // get a news item at position i
                JSONObject currentNews = resultsArray.getJSONObject(i);

                // Extract the value for the key called "title"
                String title = currentNews.getString("webTitle");

                //Extract the value for the key called "section"
                String section = currentNews.getString("sectionName");

                //Extract the value for the key called "webUrl"
                String webUrl = currentNews.getString("webUrl");

                //Extract the value for the key called "webPublicationDate"
                String webPublicationDate = currentNews.getString("webPublicationDate");

                // Get "tags" array and extract the name of the author
                JSONArray tags = currentNews.getJSONArray("tags");
                String author = null;
                for (int j = 0; j < tags.length(); j++) {
                    JSONObject currentAuthor = tags.getJSONObject(j);
                    author = "";
                    try {
                        author = "By: " + currentAuthor.getString("webTitle");
                    } catch (Exception e) {
                        Log.e(LOG_TAG, "author missing", e);
                    }

                    // create object with title, section, url and date
                    News archaeologyNews = new News(title, section, webUrl, author, webPublicationDate);

                    // add the archaeology news to the list of news
                    news.add(archaeologyNews);
                }
            }
            return news;
        } catch (JSONException e) {
            Log.e("QueryUtils", "Error Parsing Archaeology News JSON results");
        }
        return news;
    }
}