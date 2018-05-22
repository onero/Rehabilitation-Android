package dk.adamino.rehabilitation.GUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import dk.adamino.rehabilitation.BE.Exercise;
import dk.adamino.rehabilitation.BLL.YoutubeManager;
import dk.adamino.rehabilitation.Callbacks.IExerciseFirestoreCallback;
import dk.adamino.rehabilitation.GUI.Model.FirebaseExerciseModel;
import dk.adamino.rehabilitation.R;

public class YoutubeActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener, IExerciseFirestoreCallback, IActivity{

    public static final String TAG = "RehabYouTubeActivity";
    private static String API_KEY = "";

    private FirebaseExerciseModel mExerciseModel;
    private TextView mExerciseDescription, mRepetitions, mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        API_KEY = getString(R.string.youtube_api_key);

        setupViews();

        mExerciseModel = FirebaseExerciseModel.getInstance();
        mExerciseModel.loadCurrentExerciseAsync(this);

    }

    @Override
    public void setupViews() {
        mExerciseDescription = findViewById(R.id.txtExerciseDescription);
        mRepetitions = findViewById(R.id.txtRepetitions);
        mTitle = findViewById(R.id.txtTitle);

        // Setup the Youtube Fragment
        YouTubePlayerSupportFragment youTubePlayerSupportFragment = (YouTubePlayerSupportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.youtube_fragment);
        youTubePlayerSupportFragment.initialize(API_KEY, this);
    }

    private void setExerciseInfomation(Exercise exercise) {
        // Exercise information
        mTitle.setText(exercise.title);
        mRepetitions.setText(exercise.repetition);
        mExerciseDescription.setText(exercise.description);
    }

    /**
     * Called when initialization of the player succeeds.
     * @param provider
     * @param youTubePlayer
     * @param wasRestored
     */
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {

        youTubePlayer.setPlayerStateChangeListener(mPlayerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(mPlaybackEventListener);

        // Start buffering.
        if (!wasRestored) {
            String videoUrl = mExerciseModel.getCurrentExercise().videoUrl;
            String videoId = YoutubeManager.getYoutubeID(videoUrl);
            Log.d(TAG, "VideoId:" + videoId);

            youTubePlayer.cueVideo(videoId);
        }
    }

    /**
     * If the initialization fails the Toast gives the user a nice UX by displaying the failure.
     * @param provider
     * @param youTubeInitializationResult
     */
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "FAILED TO INITIALIZE", Toast.LENGTH_SHORT).show();
    }

    // Interface definition for callbacks that are invoked when video playback events occur.
    private YouTubePlayer.PlaybackEventListener mPlaybackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onPlaying() {
        // Called when playback starts, either due to play() or user action.
        }

        @Override
        public void onPaused() {
        // Called when playback is paused, either due to pause() or user action.
        }

        @Override
        public void onStopped() {
        // Called when playback stops for a reason other than being paused, such as the video ending or a playback error.
        }

        @Override
        public void onBuffering(boolean b) {
        // Called when buffering starts or ends.
        }

        @Override
        public void onSeekTo(int i) {
        // Called when a jump in playback position occurs, either due to the user scrubbing or a seek method being called (e.g.)
        }
    };

    // Interface definition for callbacks that are invoked when the high-level player state changes.
    private YouTubePlayer.PlayerStateChangeListener mPlayerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onLoading() {
        // Called when the player begins loading a video and is not ready to accept commands affecting playback (such as play() or pause()).
        }

        @Override
        public void onLoaded(String s) {
        // Called when a video has finished loading.
        }

        @Override
        public void onAdStarted() {
        // Called when playback of an advertisement starts.
        }

        @Override
        public void onVideoStarted() {
        // Called when the video reaches its end.
        }

        @Override
        public void onVideoEnded() {
        // Called when playback of the video starts.
        }

        @Override
        public void onError(YouTubePlayer.ErrorReason errorReason) {
        // Called when an error occurs.
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

    @Override
    public void onExerciseResponse(Exercise exerciseFound) {
        setExerciseInfomation(exerciseFound);
    }
}
