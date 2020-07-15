package com.omdhanwant.youstream.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.omdhanwant.youstream.R;
import com.omdhanwant.youstream.models.Video;
import com.omdhanwant.youstream.utils.FontUtility;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<Video> allVideos;
    private Context context;
    private OnVideoItemClick onVideoItemClick;


    public interface OnVideoItemClick{
        void videoItemClick(String videoId);
    }

    public VideoAdapter(Context context) {
        this.context = context;
        onVideoItemClick = (OnVideoItemClick) context;
    }

    @Override
    public VideoAdapter.VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_item, parent, false);
        return new VideoAdapter.VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoAdapter.VideoViewHolder holder, final int position) {
        holder.videoName.setText(allVideos.get(position).getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onVideoItemClick.videoItemClick(allVideos.get(position).getKey());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (allVideos == null) {
            return 0;
        }
        return allVideos.size();
    }

    public void swapDataSet(List<Video> videoList) {
        allVideos = videoList;
        if (allVideos != null) {
            notifyDataSetChanged();
        }
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView videoName;
        CardView cardView;

        public VideoViewHolder(View v) {
            super(v);
            videoName = (TextView) v.findViewById(R.id.videoItemName);
            videoName.setTypeface(FontUtility.getRegularFont(v.getContext()));
            cardView = (CardView) v.findViewById(R.id.videoListItemCardView);
        }
    }
}
