package dk.adamino.rehabilitation.Callbacks;

import dk.adamino.rehabilitation.BE.Client;

/**
 * Created by Adamino.
 */
public interface IFirestoreClientCallback {
    /**
     * Handle client found on Firestore
     * @param clientFound
     */
    void onClientResponse(Client clientFound);
}
