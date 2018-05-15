package dk.adamino.rehabilitation.DAL;

import dk.adamino.rehabilitation.Callbacks.IFirestoreClientCallback;
import dk.adamino.rehabilitation.Callbacks.IFirestoreMilestoneCallback;

/**
 * Created by Adamino.
 */
public interface IFirestore {
    /**
     * Retrieve a client from Firestore
     * @param uid
     * @return Client
     */
    void getClientByIdAsync(String uid, IFirestoreClientCallback firestoreCallback);

    /**
     * Retrieve client milestones
     * @param currentClientUid
     * @param callback
     */
    void getClientMilestones(String currentClientUid, IFirestoreMilestoneCallback callback);
}
