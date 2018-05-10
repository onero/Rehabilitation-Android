package dk.adamino.rehabilitation.GUI.Model;

import java.util.ArrayList;
import java.util.List;

import dk.adamino.rehabilitation.BE.ExerciseInfo;

public class YoutubeModel {

    private static final YoutubeModel instance = new YoutubeModel();

    private List<ExerciseInfo> mExerciseInfo;

    public static YoutubeModel getInstance() {
        return instance;
    }

    private YoutubeModel() {
        mExerciseInfo = new ArrayList<>();
    }

    public List<ExerciseInfo> getExerciseInfo() {
        return mExerciseInfo;
    }

    public void setExerciseInfo(List<ExerciseInfo> info) {
        mExerciseInfo = info;
    }
}
