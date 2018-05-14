package dk.adamino.rehabilitation.GUI.Model;

import java.util.ArrayList;
import java.util.List;

import dk.adamino.rehabilitation.BE.Exercise;
import dk.adamino.rehabilitation.Callbacks.IExerciseFirestoreCallback;
import dk.adamino.rehabilitation.DAL.FirestoreDAO;

public class FirebaseExerciseModel {

    private static FirebaseExerciseModel instance = null;
    private FirestoreDAO mFirestoreDAO;

    private List<Exercise> mExercises;

    public static FirebaseExerciseModel getInstance() {
        if(instance == null) {
            instance = new FirebaseExerciseModel();
        }
        return instance;
    }

    private FirebaseExerciseModel() {
        mExercises = new ArrayList<>();
        mFirestoreDAO = new FirestoreDAO();
    }

    public List<Exercise> getExercises() {
        return mExercises;
    }

    public void setExercises(List<Exercise> info) {
        mExercises = info;
    }

    public void loadExercisesAsync(IExerciseFirestoreCallback callback, List<String> exerciseIds) {
        mFirestoreDAO.getExercisesByIds(exerciseIds, callback);
    }

}
