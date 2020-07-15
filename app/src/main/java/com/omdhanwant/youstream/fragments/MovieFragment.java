package com.omdhanwant.youstream.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.omdhanwant.youstream.R;
import com.omdhanwant.youstream.adapters.MovieAdapter;
import com.omdhanwant.youstream.models.Movie;
import com.omdhanwant.youstream.models.MoviesResponse;
import com.omdhanwant.youstream.network.APIClient;
import com.omdhanwant.youstream.network.APIClientHelper;
import com.omdhanwant.youstream.utils.Constants;
import com.omdhanwant.youstream.utils.CustomDialog;
import com.omdhanwant.youstream.utils.FontUtility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MovieAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView noRecordLabel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        noRecordLabel = (TextView) view.findViewById(R.id.movie_list_no_items_label);
        noRecordLabel.setTypeface(FontUtility.getRegularFont(getContext()));
        noRecordLabel.setVisibility(View.GONE);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MovieAdapter(getContext());
        recyclerView.setAdapter(adapter);

        progressBar = (ProgressBar) view.findViewById(R.id.movieProgressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

        callMovieApi(false, true);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callMovieApi(true, false);
            }
        });

        return view;
    }

    private void callMovieApi(final boolean withRefresher, final boolean withLoader) {
        if (withLoader) {
            progressBar.setVisibility(View.VISIBLE);
        }
        if (withRefresher) {
            swipeRefreshLayout.setRefreshing(true);
        }
        APIClientHelper helper = APIClient.getTheAPIClient().create(APIClientHelper.class);

        Call<MoviesResponse> movieResponse = helper.getTheUpcomingMovies(Constants.API_KEY);

        movieResponse.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (withLoader) {
                    progressBar.setVisibility(View.GONE);
                }
                if (withRefresher) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                List<Movie> allMovies = response.body().getResults();

                if (allMovies == null || allMovies.size() <= 0) {
                    noRecordLabel.setVisibility(View.VISIBLE);
                } else {
                    noRecordLabel.setVisibility(View.GONE);
                }
                adapter.swapDataSet(allMovies);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                if (withLoader) {
                    progressBar.setVisibility(View.GONE);
                }
                if (withRefresher) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                CustomDialog.getTheAlertDialog(getContext(), "Internet connectivity required. Please check your network settings.");
            }
        });
    }

}
