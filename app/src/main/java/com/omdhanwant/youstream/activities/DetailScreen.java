package com.omdhanwant.youstream.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.omdhanwant.youstream.R;
import com.omdhanwant.youstream.adapters.VideoAdapter;
import com.omdhanwant.youstream.models.Video;
import com.omdhanwant.youstream.models.VideoResponse;
import com.omdhanwant.youstream.network.APIClient;
import com.omdhanwant.youstream.network.APIClientHelper;
import com.omdhanwant.youstream.utils.Constants;
import com.omdhanwant.youstream.utils.CustomDialog;
import com.omdhanwant.youstream.utils.FontUtility;
import com.omdhanwant.youstream.utils.RoundedCornersTransformation;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailScreen extends AppCompatActivity implements VideoAdapter.OnVideoItemClick {
    private Toolbar toolbar;
    private TextView movieTitleTextView;
    private TextView movieDateTextView;
    private TextView moviePopularityTextView;
    private ImageView moviePosterView;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private VideoAdapter adapter;
    private ProgressBar progressBar;
    private TextView noRecordLabel;

    private int itemId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        toolbar = (Toolbar) findViewById(R.id.itemDetailToolBar);

        movieTitleTextView = (TextView) findViewById(R.id.itemTitleTextView);
        movieTitleTextView.setTypeface(FontUtility.getRegularFont(this));

        TextView popularityLabel = (TextView) findViewById(R.id.popularityLabel);
        popularityLabel.setTypeface(FontUtility.getSemiBoldFont(this));
        moviePopularityTextView = (TextView) findViewById(R.id.popularityValue);
        moviePopularityTextView.setTypeface(FontUtility.getRegularFont(this));

        TextView releaseDateLabel = (TextView) findViewById(R.id.releaseDateLabel);
        releaseDateLabel.setTypeface(FontUtility.getSemiBoldFont(this));
        movieDateTextView = (TextView) findViewById(R.id.releaseDateValue);
        movieDateTextView.setTypeface(FontUtility.getRegularFont(this));

        moviePosterView = (ImageView) findViewById(R.id.itemPosterView);

        recyclerView = (RecyclerView) findViewById(R.id.itemVideoRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        adapter = new VideoAdapter(DetailScreen.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        progressBar = (ProgressBar) findViewById(R.id.itemVideoProgressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

        noRecordLabel = (TextView) findViewById(R.id.video_list_no_items_label);
        noRecordLabel.setTypeface(FontUtility.getRegularFont(this));
        noRecordLabel.setVisibility(View.GONE);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();

        if (intent != null) {
            String itemTitle = intent.getStringExtra("ITEM_TITLE");
            String itemReleaseDate = intent.getStringExtra("ITEM_RELEASE_DATE");
            String itemPopularityCount = intent.getStringExtra("ITEM_POPULARITY_COUNT");
            String itemPosterUrl = intent.getStringExtra("ITEM_POSTER_URL");
            itemId = intent.getIntExtra("ITEM_ID", 0);
            boolean isMovieApi = intent.getBooleanExtra("IS_MOVIE", true);

            movieTitleTextView.setText(itemTitle);
            moviePopularityTextView.setText(itemPopularityCount);
            movieDateTextView.setText(itemReleaseDate);

            final int radius = 5;
            final int margin = 5;

            Transformation transformation = new RoundedCornersTransformation(radius, margin);
            String imageUrl = Constants.IMAGE_BASE_URL + itemPosterUrl;
            Picasso.get().load(Uri.parse(imageUrl)).transform(transformation)
                    .error(R.drawable.logo_transparent)
                    .into(moviePosterView);
            getVideoDetails(itemId, isMovieApi);
        }
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


    private void getVideoDetails(int itemId, boolean isMovieApi) {
        showProgressBar();
        APIClientHelper helper = APIClient.getTheAPIClient().create(APIClientHelper.class);
        Call<VideoResponse> videoResponse;
        if (isMovieApi) {
            videoResponse = helper.getMovieVideos(itemId, Constants.API_KEY);
        } else {
            videoResponse = helper.getTVVideos(itemId, Constants.API_KEY);
        }
        videoResponse.enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                hideProgressBar();
                List<Video> allVideos = response.body().getResults();
                if (allVideos == null || allVideos.size() <= 0) {
                    noRecordLabel.setVisibility(View.VISIBLE);
                } else {
                    noRecordLabel.setVisibility(View.GONE);
                }
                adapter.swapDataSet(allVideos);
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                hideProgressBar();
                CustomDialog.getTheAlertDialog(DetailScreen.this, "Internet connectivity required. Please check your network settings.");
            }
        });
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void videoItemClick(String videoId) {
        Intent playerIntent = new Intent(DetailScreen.this, YouTubePlayer.class);
        playerIntent.putExtra("VIDEO_ID", videoId);
        startActivity(playerIntent);
    }
}
