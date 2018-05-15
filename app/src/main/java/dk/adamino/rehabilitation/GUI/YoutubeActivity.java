package dk.adamino.rehabilitation.GUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

import dk.adamino.rehabilitation.BE.Exercise;
import dk.adamino.rehabilitation.DAL.FirestoreDAO;
import dk.adamino.rehabilitation.GUI.Model.FirebaseExerciseModel;
import dk.adamino.rehabilitation.R;

public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, IActivity{

    private static final String YOUTUBE_ID = "61-X2NNwMFE";
    private static String API_KEY = "";

    private YouTubePlayerView mYouTubePlayerView;
    private FirebaseExerciseModel mExerciseModel;
    private TextView mExerciseDescription, mRepetitions, mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        setupViews();

        API_KEY = getString(R.string.youtube_api_key);

        mYouTubePlayerView.initialize(API_KEY, this);

        mExerciseModel = FirebaseExerciseModel.getInstance();

        if (mExerciseModel.getCurrentExercise() != null) {
            setExerciseInfomation(mExerciseModel.getCurrentExercise());
        } else {
            Log.e("RehabYouTubeActivity", "Current exercise wasn't set");
        }

    }

    @Override
    public void setupViews() {
        mYouTubePlayerView = findViewById(R.id.youtube_player);
        mExerciseDescription = findViewById(R.id.txtExerciseDescription);
        mRepetitions = findViewById(R.id.txtRepetitions);
        mTitle = findViewById(R.id.txtTitle);
    }

    private void setExerciseInfomation(Exercise exercise) {
        // Exercise information
        mTitle.setText(exercise.title);
        mRepetitions.setText(exercise.repetition);
        mExerciseDescription.setText(exercise.description);

        Log.d("ExerciseInfo", exercise.videoUrl);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

        youTubePlayer.setPlayerStateChangeListener(mPlayerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(mPlaybackEventListener);

        // Start buffering.
        if (!wasRestored) {
            youTubePlayer.cueVideo(YOUTUBE_ID);
        }
    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "FAILED TO INITIALIZE", Toast.LENGTH_SHORT).show();
    }

    private YouTubePlayer.PlaybackEventListener mPlaybackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {

        }

        @Override
        public void onPaused() {

        }

        @Override
        public void onStopped() {

        }

        @Override
        public void onBuffering(boolean b) {

        }

        @Override
        public void onSeekTo(int i) {

        }
    };

    private YouTubePlayer.PlayerStateChangeListener mPlayerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {

        }

        @Override
        public void onLoaded(String s) {

        }

        @Override
        public void onAdStarted() {

        }

        @Override
        public void onVideoStarted() {

        }

        @Override
        public void onVideoEnded() {

        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {

        }
    };


    /**
     * Create Intent to navigate to this activity
     * @param context
     * @return
     */
    public static Intent newIntent(Context context, Exercise currentExercise) {
        Intent intent = new Intent(context, YoutubeActivity.class);
        FirebaseExerciseModel.getInstance().setCurrentExercise(currentExercise);
        return intent;
    }
}
