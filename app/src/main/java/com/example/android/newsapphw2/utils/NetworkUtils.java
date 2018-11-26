package com.example.android.newsapphw2.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    private static final String  NEWS_BASE_URL = "https://newsapi.org/v1/articles";

    private static  final String PARAM_SOURCE = "source";

    private static  final String source = "the-next-web";

    private static final String PARAM_SORT = "sortBy";

    private  static  final String sort = "latest";

    private final static String PARAM_API_KEY = "apiKey";


    private final static String API_KEY = "43c822b373244bd2adfcd4c9a39ed02a";

    public static URL buildUrl() {
        Uri builtUri = Uri.parse(NEWS_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_SOURCE,source)
                .appendQueryParameter(PARAM_SORT,sort)
                .appendQueryParameter(PARAM_API_KEY,API_KEY)
                .build();

        URL url = null;
        try{
            url = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Copied from assignment description.
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            }
            else {
                return null;
            }
        }
        finally {
            urlConnection.disconnect();
        }
    }

}