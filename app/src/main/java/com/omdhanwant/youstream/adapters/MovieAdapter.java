package com.omdhanwant.youstream.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.omdhanwant.youstream.R;
import com.omdhanwant.youstream.models.Movie;
import com.omdhanwant.youstream.utils.Constants;
import com.omdhanwant.youstream.utils.FontUtility;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>  {
    public interface onMovieItemClick{
        void movieItemClick(Movie movie);
    }

    private List<Movie> allMovies;
    private Context context;
    private onMovieItemClick movieItemClick;

    public MovieAdapter(Context context) {
        this.context = context;
        movieItemClick = (onMovieItemClick) context;
    }

    @NonNull
    @Override
    public MovieAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        holder.movieName.setText(allMovies.get(position).getTitle());
        String imageUrl = Constants.IMAGE_BASE_URL + allMovies.get(position).getPosterPath();
        Picasso.get().load(Uri.parse(imageUrl))
                .error(R.drawable.logo_transparent)
                .into(holder.moviePosterView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieItemClick.movieItemClick(allMovies.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (allMovies == null) {
            return 0;
        }
        return allMovies.size();
    }

    public void swapDataSet(List<Movie> artistList) {
        allMovies = artistList;
        if (allMovies != null) {
            notifyDataSetChanged();
        }
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        ImageView moviePosterView;
        TextView movieName;
        CardView cardView;
        public MovieViewHolder(@NonNull View v) {
            super(v);
            moviePosterView = (ImageView) v.findViewById(R.id.itemPosterView);
            movieName = (TextView) v.findViewById(R.id.itemName);
            movieName.setTypeface(FontUtility.getRegularFont(v.getContext()));
            cardView = (CardView) v.findViewById(R.id.card_view);
        }
    }
}
