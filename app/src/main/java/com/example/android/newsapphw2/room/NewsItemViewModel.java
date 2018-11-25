package com.example.android.newsapphw2.room;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.newsapphw2.model.NewsItem;

import java.util.List;

public class NewsItemViewModel extends AndroidViewModel {

    private NewsItemRepository mRepository;

    private LiveData<List<NewsItem>> mNewsItems;

    public NewsItemViewModel(@NonNull Application application) {
        super(application);
        mRepository = new NewsItemRepository(application);
        mNewsItems = mRepository.loadAllNewsItems();
    }

    public LiveData<List<NewsItem>> getAllNewsItems() {
        return mNewsItems;
    }

    public void syncNewsItems() {
        mRepository.syncNewsItems();
    }
}
