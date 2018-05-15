package dk.adamino.rehabilitation.DAL;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import dk.adamino.rehabilitation.BE.Client;
import dk.adamino.rehabilitation.BE.Exercise;
import dk.adamino.rehabilitation.Callbacks.IExerciseFirestoreCallback;
import dk.adamino.rehabilitation.BE.Milestone;
import dk.adamino.rehabilitation.Callbacks.IFirestoreClientCallback;
import dk.adamino.rehabilitation.Callbacks.IFirestoreMilestoneCallback;

/**
 * Created by Adamino.
 */
public class FirestoreDAO implements IFirestore {

    private static final String TAG = "DAL";
    private static String USERS_COLLECTION = "Clients";
    private static String EXERCISES_COLLECTION = "Exercises";
    private static String MILESTONE_COLLECTION = "Milestones";

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    @Override
    public void getClientByIdAsync(String clientUID, final IFirestoreClientCallback firestoreCallback) {
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
    public void getExercisesById(String exerciseId, final IExerciseFirestoreCallback exerciseCallback) {

            DocumentReference docRef = mFirestore.collection(EXERCISES_COLLECTION).document(exerciseId);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Exercise exercise = document.toObject(Exercise.class);
                            exerciseCallback.onExerciseResponse(exercise);
                            Log.d(TAG, exercise.title);
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

    public void getClientMilestones(String currentClientUid, final IFirestoreMilestoneCallback callback) {
        mFirestore.collection(MILESTONE_COLLECTION)
                .whereEqualTo("clientUid", currentClientUid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Milestone> milestones = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                milestones.add(document.toObject(Milestone.class));
                            }
                            callback.onMilestoneResponse(milestones);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
}
