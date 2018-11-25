package com.example.android.newsapphw2;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.newsapphw2.room.NewsRecyclerViewAdapter;
import com.example.android.newsapphw2.model.NewsItem;
import com.example.android.newsapphw2.room.NewsItemViewModel;
import com.example.android.newsapphw2.utils.SimpleDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NewsItemViewModel mNewsItemViewModel;

    private RecyclerView mRecyclerView;
    protected NewsRecyclerViewAdapter mAdapter;
    private ArrayList<NewsItem> mNewsItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.news_recyclerview);
        mNewsItemViewModel = ViewModelProviders.of(this).get(NewsItemViewModel.class);
        mAdapter = new NewsRecyclerViewAdapter(this,mNewsItems);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.VERTICAL));

        mNewsItemViewModel.getAllNewsItems().observe(this, (List<NewsItem> items) -> {
            mAdapter.updateNewsItems(items);
            mAdapter.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            mNewsItemViewModel.syncNewsItems();
            return true;
        }
        return false;
    }
}
