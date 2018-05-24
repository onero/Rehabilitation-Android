package dk.adamino.rehabilitation.DAL;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import dk.adamino.rehabilitation.BE.Client;
import dk.adamino.rehabilitation.BE.Exercise;
import dk.adamino.rehabilitation.BE.Milestone;
import dk.adamino.rehabilitation.Callbacks.IFirestoreExerciseCallback;
import dk.adamino.rehabilitation.Callbacks.IFirestoreClientCallback;
import dk.adamino.rehabilitation.Callbacks.IFirestoreMilestoneCallback;

/**
 * Created by Adamino.
 */
public class FirestoreDAO implements IFirestore {

    private static FirestoreDAO sInstance;

    public static FirestoreDAO getInstance() {
        if (sInstance == null) {
            sInstance = new FirestoreDAO();
        }
        return sInstance;
    }

    private static final String TAG = "RehabDAL";
    private static String CLIENTS_COLLECTION = "Clients";
    private static String EXERCISES_COLLECTION = "Exercises";
    private static String MILESTONE_COLLECTION = "Milestones";

    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    private ListenerRegistration mClientListener;
    private ListenerRegistration mExerciseListener;

    private CollectionReference mClientCollection = mFirestore.collection(CLIENTS_COLLECTION);
    private CollectionReference mExerciseCollection = mFirestore.collection(EXERCISES_COLLECTION);
    private CollectionReference mMilestoneCollection = mFirestore.collection(MILESTONE_COLLECTION);

    /**
     * Remove subscriptions from Firestore!
     */
    public void removeAllListeners() {
        mClientListener.remove();
        mExerciseListener.remove();
    }

    /**
     * Gets the client by the Id.
     * @param clientUID
     * @param firestoreCallback
     */
    @Override
    public void getClientById(String clientUID, final IFirestoreClientCallback firestoreCallback) {
        DocumentReference docRef = mClientCollection.document(clientUID);
        if (mClientListener != null) {
            mClientListener.remove();
        }
         mClientListener = docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot != null) {
                    if (documentSnapshot.exists()) {
                        Client client = documentSnapshot.toObject(Client.class);
                        firestoreCallback.onClientResponse(client);
                    }
                }
            }
        });
    }

    /**
     * Gets the exercise from the clients Id.
     * @param exerciseId
     * @param exerciseCallback
     */
    @Override
    public void getExercisesByClientId(String exerciseId, final IFirestoreExerciseCallback exerciseCallback) {
        DocumentReference docRef = mExerciseCollection.document(exerciseId);
        if (mExerciseListener != null) {
            mExerciseListener.remove();
        }
        mExerciseListener = docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (documentSnapshot.exists()) {
                    Exercise exercise = documentSnapshot.toObject(Exercise.class);
                    exerciseCallback.onExerciseResponse(exercise);
                }
            }
        });
    }

    /**
     * Gets the milestone from the clients Id.
     * @param currentClientUid
     * @param callback
     */
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
