package dk.adamino.rehabilitation.DAL;

import dk.adamino.rehabilitation.Callbacks.IFirestoreExerciseCallback;
import dk.adamino.rehabilitation.Callbacks.IFirestoreClientCallback;
import dk.adamino.rehabilitation.Callbacks.IFirestoreMilestoneCallback;

/**
 * Created by Adamino.
 */
public interface IFirestore {
    /**
     * Retrieve Exercises by clientId
     * @param
     * @return Client
     */
    void getExercisesByClientId(String clientId, final IFirestoreExerciseCallback exerciseCallback);


    /**
     * Retrieve client by id
     * @param uid
     * @param firestoreCallback
     */
    void getClientById(String uid, IFirestoreClientCallback firestoreCallback);

    /**
     * Retrieve client milestones
     * @param currentClientUid
     * @param callback
     */
    void getMilestonesByClientId(String currentClientUid, IFirestoreMilestoneCallback callback);
}
