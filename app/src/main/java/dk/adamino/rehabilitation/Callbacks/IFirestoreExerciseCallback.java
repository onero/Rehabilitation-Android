package dk.adamino.rehabilitation.Callbacks;

import java.util.List;

import dk.adamino.rehabilitation.BE.Exercise;

public interface IFirestoreExerciseCallback {
    void onExerciseResponse(Exercise exerciseFound);
}
