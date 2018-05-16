package dk.adamino.rehabilitation.DAL;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import dk.adamino.rehabilitation.BE.Client;
import dk.adamino.rehabilitation.BE.Exercise;
import dk.adamino.rehabilitation.BE.Milestone;
import dk.adamino.rehabilitation.Callbacks.IExerciseFirestoreCallback;
import dk.adamino.rehabilitation.Callbacks.IFirestoreClientCallback;
import dk.adamino.rehabilitation.Callbacks.IFirestoreMilestoneCallback;

/**
 * Created by Adamino.
 */
public class FirestoreDAO implements IFirestore {

    private static final String TAG = "RehabDAL";
    private static String CLIENTS_COLLECTION = "Clients";
    private static String EXERCISES_COLLECTION = "Exercises";
    private static String MILESTONE_COLLECTION = "Milestones";

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();

    private CollectionReference mClientCollection = mFirestore.collection(CLIENTS_COLLECTION);
    private CollectionReference mExerciseCollection = mFirestore.collection(EXERCISES_COLLECTION);
    private CollectionReference mMilestoneCollection = mFirestore.collection(MILESTONE_COLLECTION);

    @Override
    public void getClientById(String clientUID, final IFirestoreClientCallback firestoreCallback) {
        DocumentReference docRef = mClientCollection.document(clientUID);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    Client client = documentSnapshot.toObject(Client.class);
                    firestoreCallback.onClientResponse(client);
                }
            }
        });
    }

    @Override
    public void getExercisesByClientId(String exerciseId, final IExerciseFirestoreCallback exerciseCallback) {
        DocumentReference docRef = mExerciseCollection.document(exerciseId);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    Exercise exercise = documentSnapshot.toObject(Exercise.class);
                    exerciseCallback.onExerciseResponse(exercise);
                }
            }
        });
    }

    @Override
    public void getMilestonesByClientId(String currentClientUid, final IFirestoreMilestoneCallback callback) {
        mMilestoneCollection
                .whereEqualTo("clientUid", currentClientUid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        List<Milestone> milestones = new ArrayList<>();
                        for (QueryDocumentSnapshot document : value) {
                            if (document != null) {
                                milestones.add(document.toObject(Milestone.class));
                            }
                        }
                        callback.onMilestoneResponse(milestones);
                    }
                });
    }
}
