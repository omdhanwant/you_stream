package com.omdhanwant.youstream.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omdhanwant.youstream.R;
import com.omdhanwant.youstream.adapters.SearchAdapter;
import com.omdhanwant.youstream.models.Search;
import com.omdhanwant.youstream.models.SearchResponse;
import com.omdhanwant.youstream.network.APIClient;
import com.omdhanwant.youstream.network.APIClientHelper;
import com.omdhanwant.youstream.utils.Constants;
import com.omdhanwant.youstream.utils.CustomDialog;
import com.omdhanwant.youstream.utils.FontUtility;

import java.lang.reflect.Field;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchScreen extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchAdapter.onSearchItemClick{

    private SearchView searchView;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private SearchAdapter adapter;
    private ProgressBar progressBar;
    private TextView noRecordsLabel;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        toolbar = (Toolbar) findViewById(R.id.search_tool_bar);
        TextView searchTitle = (TextView) findViewById(R.id.search_title_view);
        searchTitle.setTypeface(FontUtility.getRegularFont(this));


        noRecordsLabel = (TextView) findViewById(R.id.search_list_no_items_label);
        noRecordsLabel.setTypeface(FontUtility.getRegularFont(this));
        noRecordsLabel.setVisibility(View.GONE);

        recyclerView = (RecyclerView) findViewById(R.id.searchRecyclerView);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SearchAdapter(this);
        recyclerView.setAdapter(adapter);

        progressBar = (ProgressBar) findViewById(R.id.searchProgressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        progressBar.setVisibility(View.GONE);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        customizedSearchBar();
    }

    private void customizedSearchBar() {
        searchView = (SearchView) findViewById(R.id.searchView);

        this.searchView.setIconifiedByDefault(false);
        this.searchView.setOnQueryTextListener(this);
        this.searchView.setQueryHint("Type Here");
        this.searchView.setInputType(1);

        TextView mQueryTextView = (AutoCompleteTextView) searchView.findViewById(R.id.search_src_text);

        if (mQueryTextView != null) {
            mQueryTextView.setTextColor(Color.DKGRAY);
            mQueryTextView.setHintTextColor(Color.DKGRAY);
            mQueryTextView.setTypeface(FontUtility.getRegularFont(this));
        }
        try {
            Field mCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            mCursorDrawableRes.setAccessible(true);
            mCursorDrawableRes.set(mQueryTextView, 0);
        } catch (Exception ignored) {
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        if (!query.isEmpty()) {
            //Write the api handling code
            getTheSearchResultForQuery(query);
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getTheSearchResultForQuery(String query) {
        progressBar.setVisibility(View.VISIBLE);
        APIClientHelper helper = APIClient.getTheAPIClient().create(APIClientHelper.class);

        Call<SearchResponse> searchResponse = helper.getSearchResult(Constants.API_KEY, query);

        searchResponse.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                progressBar.setVisibility(View.GONE);
                List<Search> allSearch = response.body().getResults();

                if (allSearch == null || allSearch.size() <= 0) {
                    noRecordsLabel.setVisibility(View.VISIBLE);
                } else {
                    noRecordsLabel.setVisibility(View.GONE);
                }
                adapter.swapDataSet(allSearch);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                CustomDialog.getTheAlertDialog(SearchScreen.this, "No result found.");
            }
        });
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void searchItemClick(Search search) {
        Intent searchDetailIntent = new Intent(SearchScreen.this, DetailScreen.class);
        if (search.getMediaType().equalsIgnoreCase("tv")) {
            searchDetailIntent.putExtra("ITEM_TITLE", search.getOriginalName());
            searchDetailIntent.putExtra("ITEM_RELEASE_DATE", search.getFirstAirDate());
            searchDetailIntent.putExtra("IS_MOVIE", false);
        } else {
            searchDetailIntent.putExtra("ITEM_TITLE", search.getOriginalTitle());
            searchDetailIntent.putExtra("ITEM_RELEASE_DATE", search.getReleaseDate());
            searchDetailIntent.putExtra("IS_MOVIE", true);
        }
        searchDetailIntent.putExtra("ITEM_POPULARITY_COUNT", search.getPopularity().toString());
        searchDetailIntent.putExtra("ITEM_POSTER_URL", search.getPosterPath());
        searchDetailIntent.putExtra("ITEM_ID", search.getId());
        startActivity(searchDetailIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        searchView.setQuery("", false);
        searchView.clearFocus();
    }
}
