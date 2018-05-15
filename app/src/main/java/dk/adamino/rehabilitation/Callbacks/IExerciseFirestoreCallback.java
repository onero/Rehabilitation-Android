package dk.adamino.rehabilitation.Callbacks;

import java.util.List;

import dk.adamino.rehabilitation.BE.Exercise;

public interface IExerciseFirestoreCallback {
    void onExerciseResponse(Exercise exerciseFound);
}
