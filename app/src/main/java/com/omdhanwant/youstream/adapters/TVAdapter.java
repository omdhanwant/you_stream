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
import com.omdhanwant.youstream.models.TV;
import com.omdhanwant.youstream.utils.Constants;
import com.omdhanwant.youstream.utils.FontUtility;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TVAdapter extends RecyclerView.Adapter<TVAdapter.TVShowViewHolder>{
    private List<TV> allTVShows;
    private Context context;
    private onTVItemClick ontvItemClick;

    public TVAdapter(Context context) {
        this.context = context;
        this.ontvItemClick = (onTVItemClick)context;
    }

    public interface onTVItemClick{
        void tvItemClick(TV tv);
    }

    @Override
    public TVAdapter.TVShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_list_item, parent, false);
        return new TVAdapter.TVShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TVAdapter.TVShowViewHolder holder, final int position) {
        holder.tvShowName.setText(allTVShows.get(position).getName());
        String imageUrl = Constants.IMAGE_BASE_URL + allTVShows.get(position).getPosterPath();
        Picasso.get().load(Uri.parse(imageUrl))
                .error(R.drawable.logo_transparent)
                .into(holder.tvShowPosterView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ontvItemClick.tvItemClick(allTVShows.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if (allTVShows == null) {
            return 0;
        }
        return allTVShows.size();
    }

    public void swapDataSet(List<TV> tvList) {
        allTVShows = tvList;
        if (allTVShows != null) {
            notifyDataSetChanged();
        }
    }

    public static class TVShowViewHolder extends RecyclerView.ViewHolder {
        ImageView tvShowPosterView;
        TextView tvShowName;
        CardView cardView;

        public TVShowViewHolder(View v) {
            super(v);
            tvShowPosterView = (ImageView) v.findViewById(R.id.itemPosterView);
            tvShowName = (TextView) v.findViewById(R.id.itemName);
            tvShowName.setTypeface(FontUtility.getRegularFont(v.getContext()));
            cardView = (CardView) v.findViewById(R.id.card_view);
        }
    }
}
