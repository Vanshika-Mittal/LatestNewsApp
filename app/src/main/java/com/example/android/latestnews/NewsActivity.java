package com.example.android.latestnews;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>{

    //Times of India API
    private static final String TOI_REQUEST_URL =
            "https://newsapi.org/v1/articles?source=the-times-of-india&sortBy=latest&apiKey=7f22a5f4b93c43d093737f2441ca2cc0";
    //CNBC API
    private static final String CNBC_REQUEST_URL =
            "https://newsapi.org/v1/articles?source=cnbc&sortBy=top&apiKey=7f22a5f4b93c43d093737f2441ca2cc0";
    //New York Times API
    private static final String NYT_REQUEST_URL =
            "https://newsapi.org/v1/articles?source=the-new-york-times&sortBy=top&apiKey=7f22a5f4b93c43d093737f2441ca2cc0";
    //Bloomsbury API
    private static final String BLOOMBURG_REQUEST_URL = 
            "https://newsapi.org/v1/articles?source=bloomberg&sortBy=top&apiKey=7f22a5f4b93c43d093737f2441ca2cc0";
    //Economist API
    private static final String ECONOMIST_REQUEST_URL =
            "https://newsapi.org/v1/articles?source=the-economist&sortBy=top&apiKey=7f22a5f4b93c43d093737f2441ca2cc0";

    //The url to be queried.
    private String mFinalQuery;

    // Create a new adapter that takes an empty list of news as input
    private NewsAdapter mAdapter;

    //The empty view to be displayed when there are no results in the list view.
    private TextView mEmptyStateTextView;

    // The indicator that shows the results are still loading.
    private View mLoadingIndicator;

    // The information URL
    public static String mInfoUrl;

    //The heading of the story
    public static String mStoryHeading;

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        mAdapter.clear();
        mLoadingIndicator.setVisibility(View.VISIBLE);
        mEmptyStateTextView.setText("");

        return new NewsLoader(this, mFinalQuery);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        mEmptyStateTextView.setText("No articles found.");

        mLoadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem mSpinnerItem1 = null;
        mSpinnerItem1 = menu.findItem(R.id.spinner);
        View view1 = mSpinnerItem1.getActionView();

        ArrayAdapter mAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spinner_list_item_array, android.R.layout.simple_spinner_dropdown_item);
        mAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        if (view1 instanceof Spinner) {
            final Spinner spinner = (Spinner) view1;
            spinner.setAdapter(mAdapter);
            spinner.getBackground().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String newsQuery = spinner.getSelectedItem().toString();
                    switch (newsQuery) {

                        case "Times of India":
                            mFinalQuery = TOI_REQUEST_URL;
                            break;
                        case "New York Times":
                            mFinalQuery = NYT_REQUEST_URL;
                            break;
                        case "CNBC":
                            mFinalQuery = CNBC_REQUEST_URL;
                            break;
                        case "Bloomburg":
                            mFinalQuery = BLOOMBURG_REQUEST_URL;
                            break;
                        case "The Economist":
                            mFinalQuery = ECONOMIST_REQUEST_URL;
                            break;
                        default:
                            mFinalQuery = TOI_REQUEST_URL;
                            break;
                    }
                    ConnectivityManager connectivityManager =
                            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    // If there is a network connection, fetch data
                    if (networkInfo != null && networkInfo.isConnected()) {
                        // Get a reference to the LoaderManager, in order to interact with loaders.
                        LoaderManager loaderManager = getLoaderManager();

                        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                        // because this activity implements the LoaderCallbacks interface).
                        loaderManager.restartLoader(1, null, NewsActivity.this);
                    } else {
                        // Hide loading indicator because the data has been loaded

                        mLoadingIndicator.setVisibility(View.GONE);

                        mEmptyStateTextView.setText("No internet connection.");
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    mFinalQuery = TOI_REQUEST_URL;
                    ConnectivityManager connectivityManager =
                            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                    // If there is a network connection, fetch data
                    if (networkInfo != null && networkInfo.isConnected()) {
                        // Get a reference to the LoaderManager, in order to interact with loaders.
                        LoaderManager loaderManager = getLoaderManager();

                        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                        // because this activity implements the LoaderCallbacks interface).
                        loaderManager.restartLoader(1, null, NewsActivity.this);
                    } else {
                        // Hide loading indicator because the data has been loaded
                        mLoadingIndicator.setVisibility(View.GONE);

                        mEmptyStateTextView.setText("No internet connection.");
                    }
                }
            });

        }
        return true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        // Create a new adapter that takes an empty list of news as input
        mAdapter = new NewsAdapter(this, new ArrayList<News>());


        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = (ListView) findViewById(R.id.list);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        newsListView.setAdapter(mAdapter);

        //Set the empty state view for the list view.
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        //Find the loading indicator and set it to gone
        mLoadingIndicator = findViewById(R.id.loading_indicator);

        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News story = mAdapter.getItem(position);
                mStoryHeading = story.getNewsHeading();
                mInfoUrl = story.getNewsUrl();

                Intent webIntent = new Intent(NewsActivity.this, WebActivity.class);
                startActivity(webIntent);
            }
        });
    }


    private static class NewsLoader extends AsyncTaskLoader<List<News>> {

        private String mUrl;

        /**
         * Constructs a new {@link com.example.android.latestnews.NewsActivity.NewsLoader}.
         *
         * @param context of the activity
         * @param url     to load data from
         */
        public NewsLoader(Context context, String url) {
            super(context);
            mUrl = url;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        @Override
        public List<News> loadInBackground() {
            if (mUrl == null) {
                return null;
            }

            List<News> news = QueryUtils.fetchNewsData(mUrl);
            return news;
        }
    }

    public String getInfoUrl() {
        return mInfoUrl;
    }

    public String getStoryHeading() {
        return mStoryHeading;
    }
}


