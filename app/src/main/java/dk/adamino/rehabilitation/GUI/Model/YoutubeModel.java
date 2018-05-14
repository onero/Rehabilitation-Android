package dk.adamino.rehabilitation.GUI.Model;

import java.util.ArrayList;
import java.util.List;

import dk.adamino.rehabilitation.BE.Exercise;

public class YoutubeModel {

    private static final YoutubeModel instance = new YoutubeModel();

    private List<Exercise> mExercise;

    public static YoutubeModel getInstance() {
        return instance;
    }

    private YoutubeModel() {
        mExercise = new ArrayList<>();
    }

    public List<Exercise> getExercise() {
        return mExercise;
    }

    public void setExercise(List<Exercise> info) {
        mExercise = info;
    }
}
