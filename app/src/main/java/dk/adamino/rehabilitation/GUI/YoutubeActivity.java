package dk.adamino.rehabilitation.GUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dk.adamino.rehabilitation.BE.Exercise;
import dk.adamino.rehabilitation.GUI.Model.FirebaseExerciseModel;
import dk.adamino.rehabilitation.R;

public class YoutubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, IActivity{

    public static final String TAG = "RehabYouTubeActivity";
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
            Log.e(TAG, "Current exercise wasn't set");
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
    }

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
