package dk.adamino.rehabilitation.DAL;

import dk.adamino.rehabilitation.BE.Client;

/**
 * Created by Adamino.
 */
public interface IFirestore {
    /**
     * Retrieve a client from Firestore
     * @param uid
     * @return Client
     */
    Client getClientById(String uid);
}
