package com.example.android.newsapphw2.room;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.newsapphw2.R;
import com.example.android.newsapphw2.model.NewsItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsItemHolder> {




    private Context mContext;
    private ArrayList<NewsItem> mNewsItems;

    public NewsRecyclerViewAdapter(Context context, ArrayList<NewsItem> newsItems) {
        this.mContext = context;
        this.mNewsItems = newsItems;
    }

    @NonNull
    @Override
    public NewsRecyclerViewAdapter.NewsItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.news_item, viewGroup, shouldAttachToParentImmediately);
        NewsItemHolder viewHolder = new NewsItemHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerViewAdapter.NewsItemHolder newsItemHolder, int i) {
        newsItemHolder.bind(i);
    }

    @Override
    public int getItemCount() {
        return mNewsItems.size();
    }

    public class NewsItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView author;
        private TextView title;
        private TextView description;
        private TextView date;
        private ImageView image;
        public NewsItemHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
            image = itemView.findViewById(R.id.imageViewThumbnail);
        }

        public void bind(int index) {
            NewsItem newsItem = mNewsItems.get(index);
            author.setText(newsItem.getAuthor());
            title.setText("Title: " + newsItem.getTitle());
            description.setText("Description: " + newsItem.getDescription());
            date.setText("Date: " + newsItem.getPublishedAt());

            Uri uri = Uri.parse(mNewsItems.get(index).getUrlToImage());
            if (uri != null) {
                Picasso.get().load(uri).into(image);

            }


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            NewsItem newsItem = mNewsItems.get(getAdapterPosition());
            String url = newsItem.getUrl();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            mContext.startActivity(intent);

        }
    }

    public void updateNewsItemRepo(List<NewsItem> newsItems) {
        mNewsItems.addAll(newsItems);
        notifyDataSetChanged();
    }
}
