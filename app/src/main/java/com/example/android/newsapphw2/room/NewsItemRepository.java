package com.example.android.newsapphw2.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.android.newsapphw2.model.NewsItem;
import com.example.android.newsapphw2.utils.JSONUtils;
import com.example.android.newsapphw2.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewsItemRepository {

    private NewsItemDao mNewsItemDao;
    private LiveData<List<NewsItem>> mNewsItems;

    public NewsItemRepository(Application application) {
        NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application.getApplicationContext());
        mNewsItemDao = db.newsItemDao();
        mNewsItems = mNewsItemDao.loadAllNewsItems();
    }

    public LiveData<List<NewsItem>> loadAllNewsItems() {
        return mNewsItems;
    }

    public void syncNewsItems() {
        new SyncNewsItemsTask(mNewsItemDao).execute();
    }

    private static class SyncNewsItemsTask extends AsyncTask<Void, Void, Void> {

        private NewsItemDao mAsyncTaskDao;

        SyncNewsItemsTask(NewsItemDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void ...objects) {

            // Clear all current entries in the database.
            mAsyncTaskDao.clearAll();


//            get a result string
            String responseBody = null;
            try {

                // Call the API and get a result string.
                responseBody =  NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrl());
            }
            catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            // Parse results into a list of news objects.
            ArrayList<NewsItem> newsItems = JSONUtils.parseNews(responseBody);

            // Persist news into database
            mAsyncTaskDao.insert(newsItems);

            return null;
        }

    }
}
