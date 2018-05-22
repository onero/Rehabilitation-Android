package dk.adamino.rehabilitation.GUI.Model;

import java.util.ArrayList;
import java.util.List;

import dk.adamino.rehabilitation.BE.Exercise;
import dk.adamino.rehabilitation.BLL.FirebaseFacade;
import dk.adamino.rehabilitation.Callbacks.IFirestoreExerciseCallback;

public class FirebaseExerciseModel {

    private static FirebaseExerciseModel instance = null;
    private Exercise mCurrentExercise;
    private FirebaseFacade mFirebaseFacade;

    private List<Exercise> mExercises;

    public static FirebaseExerciseModel getInstance() {
        if(instance == null) {
            instance = new FirebaseExerciseModel();
        }
        return instance;
    }

    private FirebaseExerciseModel() {
        mExercises = new ArrayList<>();
        mFirebaseFacade = FirebaseFacade.getInstance();
    }

    public List<Exercise> getExercises() {
        return mExercises;
    }

    public void setExercises(List<Exercise> info) {
        mExercises = info;
    }

    /**
     * Get the current exercise.
     * @return
     */
    public Exercise getCurrentExercise() {
        return mCurrentExercise;
    }

    /**
     * Sets the current exercise.
     * @param currentExercise
     */
    public void setCurrentExercise(Exercise currentExercise) {
        mCurrentExercise = currentExercise;
    }

    public void loadCurrentExerciseAsync(IFirestoreExerciseCallback callback) {
        mFirebaseFacade.getFirestoreDAO().getExercisesByClientId(mCurrentExercise.uid, callback);
    }
}
