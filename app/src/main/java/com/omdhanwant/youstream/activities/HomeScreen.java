package com.omdhanwant.youstream.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.omdhanwant.youstream.R;
import com.omdhanwant.youstream.adapters.MovieAdapter;
import com.omdhanwant.youstream.adapters.TVAdapter;
import com.omdhanwant.youstream.fragments.MovieFragment;
import com.omdhanwant.youstream.fragments.TVFragment;
import com.omdhanwant.youstream.models.Movie;
import com.omdhanwant.youstream.models.TV;
import com.omdhanwant.youstream.utils.FontUtility;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity implements MovieAdapter.onMovieItemClick, TVAdapter.onTVItemClick {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private TextView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        titleView = (TextView) findViewById(R.id.home_title_view);


        titleView.setTypeface(FontUtility.getSemiBoldFont(this));
        setSupportActionBar(toolbar);


        // setup tab layout with view pager
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MovieFragment(), "Movies");
        adapter.addFragment(new TVFragment(), "TV Shows");
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void movieItemClick(Movie movie) {
        Intent movieDetailIntent = new Intent(HomeScreen.this, DetailScreen.class);
        movieDetailIntent.putExtra("ITEM_TITLE", movie.getOriginalTitle());
        movieDetailIntent.putExtra("ITEM_RELEASE_DATE", movie.getReleaseDate());
        movieDetailIntent.putExtra("ITEM_POPULARITY_COUNT", movie.getPopularity().toString());
        movieDetailIntent.putExtra("ITEM_POSTER_URL", movie.getPosterPath());
        movieDetailIntent.putExtra("ITEM_ID", movie.getId());
        movieDetailIntent.putExtra("IS_MOVIE",true);
        startActivity(movieDetailIntent);
    }

    @Override
    public void tvItemClick(TV tv) {
        Intent tvDetailIntent = new Intent(HomeScreen.this, DetailScreen.class);
        tvDetailIntent.putExtra("ITEM_TITLE", tv.getOriginalName());
        tvDetailIntent.putExtra("ITEM_RELEASE_DATE", tv.getFirstAirDate());
        tvDetailIntent.putExtra("ITEM_POPULARITY_COUNT", tv.getPopularity().toString());
        tvDetailIntent.putExtra("ITEM_POSTER_URL", tv.getPosterPath());
        tvDetailIntent.putExtra("ITEM_ID", tv.getId());
        tvDetailIntent.putExtra("IS_MOVIE",false);
        startActivity(tvDetailIntent);
    }


    // View Pager Adapter
    static class ViewPagerAdapter extends FragmentPagerAdapter {

        List<Fragment> allFragments = new ArrayList<>();
        List<String> allFragmentsTitles = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return allFragmentsTitles.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return allFragments.get(position);
        }

        @Override
        public int getCount() {
            return allFragments.size();
        }

        public void addFragment(Fragment fragment, String fragmentTitle) {
            allFragments.add(fragment);
            allFragmentsTitles.add(fragmentTitle);
        }
    }


    // Menu search option
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.search) {
            Intent searchIntent = new Intent(this,SearchScreen.class);
            startActivity(searchIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}