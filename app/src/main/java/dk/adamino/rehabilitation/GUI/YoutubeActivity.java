package dk.adamino.rehabilitation.GUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dk.adamino.rehabilitation.BE.Exercise;
import dk.adamino.rehabilitation.GUI.Model.FirebaseExerciseModel;
import dk.adamino.rehabilitation.R;

public class YoutubeActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener, IActivity{

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

        // If current exercise != null, set the info.
        if (mExerciseModel.getCurrentExercise() != null) {
            setExerciseInfomation(mExerciseModel.getCurrentExercise());
        } else {
            Log.e(TAG, "Current exercise wasn't set");
        }

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
            String videoId = getYoutubeID(videoUrl);
            Log.d(TAG, "VideoId:" + videoId);

            youTubePlayer.cueVideo(videoId);
        }
    }

    /**
     * Extract the videoId from the
     * @param youtubeUrl
     * @return
     */
    // TODO ALH: Refactor to BLL! UnitTest?
    private static String getYoutubeID(String youtubeUrl) {

        if (TextUtils.isEmpty(youtubeUrl)) {
            return "";
        }
        String video_id = "";

        // Very awesome regex specifically for youtube...
        String expression = "^.*((youtu.be" + "\\/)" + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*"; // var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
        CharSequence input = youtubeUrl;
        // Exgtract expression from url
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        // Check for regex match
        if (matcher.matches()) {
            String groupIndex1 = matcher.group(7);
            if (groupIndex1 != null && groupIndex1.length() == 11)
                video_id = groupIndex1;
        }
        // Handle empty id
        if (TextUtils.isEmpty(video_id)) {
            if (youtubeUrl.contains("youtu.be/")  ) {
                String spl = youtubeUrl.split("youtu.be/")[1];
                if (spl.contains("\\?")) {
                    video_id = spl.split("\\?")[0];
                }else {
                    video_id =spl;
                }

            }
        }

        return video_id;
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
}
