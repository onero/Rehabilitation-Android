package dk.adamino.rehabilitation.BE;

import android.media.Image;

public class ExerciseInfo {
    public String mTitle;
    public String mRepetitions;
    public String mDescription;
    public Image mYoutubeImage;

    public ExerciseInfo (String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getRepetitions() {
        return mRepetitions;
    }
}
