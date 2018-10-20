package com.example.android.guardiannewsfeedapp;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class NewsFeedActivity extends AppCompatActivity
                    implements LoaderCallbacks<List<NewsFeed>> {

                private static final String LOG_TAG = NewsFeedActivity.class.getName();
                private TextView EmptyTextView;
                private static final String USGS_REQUEST_URL =
                        "https://content.guardianapis.com/search?api-key=4ceac05c-40f9-4e27-978c-d47d0cc072d8";
                private static final int NEWSFEED_LOADER_ID = 1;
                private NewsFeedAdapter mAdapter;
                @Override
                protected void onCreate(Bundle savedInstanceState) {
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.newsfeed_activity);
                    ListView newsfeedListView = (ListView) findViewById(R.id.list);
                    mAdapter = new NewsFeedAdapter(this, new ArrayList<NewsFeed>());
                    newsfeedListView.setAdapter(mAdapter);
                    EmptyTextView = (TextView) findViewById(R.id.empty_view);
                    newsfeedListView.setEmptyView(EmptyTextView);
                    newsfeedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            NewsFeed currentnews = mAdapter.getItem(position);
                            Uri newsfeedUri = Uri.parse(currentnews.getWebUrl());
                            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsfeedUri);
                            startActivity(websiteIntent);
                        }
                    });
                ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                LoaderManager loaderManager = getLoaderManager();
                loaderManager.initLoader(NEWSFEED_LOADER_ID, null, this);
                } else {
                View loadingIndicator = findViewById(R.id.loading_indicator);
                loadingIndicator.setVisibility(View.GONE);
                EmptyTextView.setText(R.string.no_internet);
                }
                }
                @Override
                public Loader<List<NewsFeed>> onCreateLoader(int i, Bundle bundle) {
                    return new NewsFeedLoader(this, USGS_REQUEST_URL);
                }
                @Override
                public void onLoadFinished(Loader<List<NewsFeed>> loader, List<NewsFeed> newsFeeds) {
                    mAdapter.clear();
                    EmptyTextView.setText(R.string.no_news);
                    if (newsFeeds != null && !newsFeeds.isEmpty()) {
                        mAdapter.addAll(newsFeeds);
                    }
                }
                @Override
                public void onLoaderReset(Loader<List<NewsFeed>> loader) {
                    mAdapter.clear();
                }
}

