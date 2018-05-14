package dk.adamino.rehabilitation.DAL;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import dk.adamino.rehabilitation.BE.Client;
import dk.adamino.rehabilitation.BE.Exercise;
import dk.adamino.rehabilitation.Callbacks.IExerciseFirestoreCallback;
import dk.adamino.rehabilitation.Callbacks.IFirestoreCallback;

/**
 * Created by Adamino.
 */
public class FirestoreDAO implements IFirestore {

    private static final String TAG = "DAL";

    private static String USERS_COLLECTION = "Clients";
    private static String EXERCISES_COLLECTION = "Exercises";

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    @Override
    public void getClientByIdAsync(String clientUID, final IFirestoreCallback firestoreCallback) {
        DocumentReference docRef = mFirestore.collection(USERS_COLLECTION).document(clientUID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Client client = task.getResult().toObject(Client.class);
                        firestoreCallback.onClientResponse(client);
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public void getExercisesByIds(List<String> exerciseIds, final IExerciseFirestoreCallback exerciseCallback) {
        final List<Exercise> exercises = new ArrayList<>();

        for (String id : exerciseIds) {
            DocumentReference docRef = mFirestore.collection(EXERCISES_COLLECTION).document(id);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Exercise exercise = task.getResult().toObject(Exercise.class);
                            exercises.add(exercise);
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "Get fail with: ", task.getException());
                    }
                }
            });
        }
        exerciseCallback.onExercisesResponse(exercises);
    }


}
