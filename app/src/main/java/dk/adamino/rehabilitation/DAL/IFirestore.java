package dk.adamino.rehabilitation.DAL;

import dk.adamino.rehabilitation.GUI.IFirestoreResponse;

/**
 * Created by Adamino.
 */
public interface IFirestore {
    /**
     * Retrieve a client from Firestore
     * @param uid
     * @return Client
     */
    void getClientById(String uid, IFirestoreResponse response);
}
