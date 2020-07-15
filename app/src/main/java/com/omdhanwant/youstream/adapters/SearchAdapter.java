package com.omdhanwant.youstream.adapters;


import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.omdhanwant.youstream.R;
import com.omdhanwant.youstream.models.Search;
import com.omdhanwant.youstream.utils.Constants;
import com.omdhanwant.youstream.utils.FontUtility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {


    private List<Search> allSearchResult;
    private Context context;
    private SearchAdapter.onSearchItemClick searchItemClick;

    public SearchAdapter(Context context) {
        this.context = context;
        searchItemClick = (SearchAdapter.onSearchItemClick) context;
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_list_item, parent, false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.SearchViewHolder holder, final int position) {
        Search searchObject = allSearchResult.get(position);

        if (searchObject.getMediaType().equalsIgnoreCase("tv")) {
            holder.movieName.setText(searchObject.getName());
        } else {
            holder.movieName.setText(searchObject.getTitle());
        }
        String imageUrl = Constants.IMAGE_BASE_URL + allSearchResult.get(position).getPosterPath();
        Picasso.get().load(Uri.parse(imageUrl))
                .error(R.drawable.logo_transparent)
                .into(holder.moviePosterView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchItemClick.searchItemClick(allSearchResult.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (allSearchResult == null) {
            return 0;
        }
        return allSearchResult.size();
    }

    public void swapDataSet(List<Search> searchList) {
        List<Search> refinedSearchList = new ArrayList<>();
        if (searchList != null) {
            for (Search currentItem :
                    searchList) {
                if (currentItem.getMediaType().equalsIgnoreCase("person")) {
                    continue;
                }
                refinedSearchList.add(currentItem);
            }
            allSearchResult = refinedSearchList;
            notifyDataSetChanged();
        }
    }

    public interface onSearchItemClick {
        void searchItemClick(Search search);
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePosterView;
        TextView movieName;
        CardView cardView;

        public SearchViewHolder(View v) {
            super(v);
            moviePosterView = (ImageView) v.findViewById(R.id.itemPosterView);
            movieName = (TextView) v.findViewById(R.id.itemName);
            movieName.setTypeface(FontUtility.getRegularFont(v.getContext()));
            cardView = (CardView) v.findViewById(R.id.card_view);
        }
    }
}
