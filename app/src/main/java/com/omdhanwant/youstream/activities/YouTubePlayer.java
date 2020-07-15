package com.omdhanwant.youstream.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayerView;
import com.omdhanwant.youstream.R;
import com.omdhanwant.youstream.utils.Constants;

public class YouTubePlayer extends YouTubeBaseActivity implements com.google.android.youtube.player.YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;
    // YouTube player view
    private YouTubePlayerView youTubeViewPlayerView;

    private String VIDEO_ID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.youtube_player);

        youTubeViewPlayerView = (YouTubePlayerView) findViewById(R.id.youtube_view);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("VIDEO_ID")) {
                VIDEO_ID = intent.getStringExtra("VIDEO_ID");
            }
        }

        // Initializing video player with developer key
        youTubeViewPlayerView.initialize(Constants.DEVELOPER_KEY, this);
    }

    @Override
    public void onInitializationSuccess(com.google.android.youtube.player.YouTubePlayer.Provider provider, com.google.android.youtube.player.YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(VIDEO_ID);
    }

    @Override
    public void onInitializationFailure(com.google.android.youtube.player.YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

}
