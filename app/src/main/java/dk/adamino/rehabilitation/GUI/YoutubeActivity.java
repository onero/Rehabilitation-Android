package dk.adamino.rehabilitation.GUI;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import dk.adamino.rehabilitation.R;

public class YoutubeActivity extends YouTubeBaseActivity {

    private static final String YOUTUBE_ID = "a4NT5iBFuZs";
    private static final String API_KEY = "AIzaSyAh7ZH9nhpNKapBwAHzoo_da9TIB9__7G4";

    private YouTubePlayerView mYouTubePlayerView;
    private Button mButton;
    private YouTubePlayer.OnInitializedListener mOnInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        mYouTubePlayerView = findViewById(R.id.youtube_player);
        mButton = findViewById(R.id.play_video);

        mOnInitializedListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(YOUTUBE_ID);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mYouTubePlayerView.initialize(API_KEY, mOnInitializedListener);
            }
        });
    }
}
