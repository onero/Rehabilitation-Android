package dk.adamino.rehabilitation.Callbacks;

import dk.adamino.rehabilitation.BE.Client;

/**
 * Created by Adamino.
 */
public interface IFirestoreCallback {
    /**
     * Handle client found on Firestore
     * @param clientFound
     */
    void onClientResponse(Client clientFound);
}
