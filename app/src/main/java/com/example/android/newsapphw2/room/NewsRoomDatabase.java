package com.example.android.newsapphw2.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.newsapphw2.model.NewsItem;

@Database(entities = {NewsItem.class}, version = 1,exportSchema = false)

public abstract class NewsRoomDatabase extends RoomDatabase {

    public abstract NewsItemDao newsItemDao();

    public static final String DATABASE_NAME = "news_database";

    private static volatile NewsRoomDatabase INSTANCE;

    public static NewsRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (NewsRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            NewsRoomDatabase.class,
                            DATABASE_NAME
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
