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
import com.omdhanwant.youstream.adapters.TVAdapter;
import com.omdhanwant.youstream.models.TV;
import com.omdhanwant.youstream.models.TVResponse;
import com.omdhanwant.youstream.network.APIClient;
import com.omdhanwant.youstream.network.APIClientHelper;
import com.omdhanwant.youstream.utils.Constants;
import com.omdhanwant.youstream.utils.CustomDialog;
import com.omdhanwant.youstream.utils.FontUtility;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TVFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TVAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView noRecordLabel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv, container, false);
        noRecordLabel = (TextView) view.findViewById(R.id.tv_list_no_items_label);
        noRecordLabel.setTypeface(FontUtility.getRegularFont(getContext()));
        noRecordLabel.setVisibility(View.GONE);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.tvswipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callTvApi(true, false);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.tvrecyclerView);
        layoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TVAdapter(getContext());
        recyclerView.setAdapter(adapter);

        progressBar = (ProgressBar) view.findViewById(R.id.tvProgressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);

        callTvApi(false, true);
        return view;
    }


    private void callTvApi(final boolean withRefresher, final boolean withLoader) {
        if (withLoader) {
            progressBar.setVisibility(View.VISIBLE);
        }
        if (withRefresher) {
            swipeRefreshLayout.setRefreshing(true);
        }

        APIClientHelper helper = APIClient.getTheAPIClient().create(APIClientHelper.class);

        Call<TVResponse> tvShowsResponse = helper.getTheUpcomingTVShows(Constants.API_KEY);

        tvShowsResponse.enqueue(new Callback<TVResponse>() {
            @Override
            public void onResponse(Call<TVResponse> call, Response<TVResponse> response) {
                if (withLoader) {
                    progressBar.setVisibility(View.GONE);
                }
                if (withRefresher) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                List<TV> allTVShows = response.body().getResults();

                if (allTVShows == null || allTVShows.size() <= 0) {
                    noRecordLabel.setVisibility(View.VISIBLE);
                } else {
                    noRecordLabel.setVisibility(View.GONE);
                }
                adapter.swapDataSet(allTVShows);
            }

            @Override
            public void onFailure(Call<TVResponse> call, Throwable t) {
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
