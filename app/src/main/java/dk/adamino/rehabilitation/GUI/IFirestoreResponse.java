package dk.adamino.rehabilitation.GUI;

import dk.adamino.rehabilitation.BE.Client;

/**
 * Created by Adamino.
 */
public interface IFirestoreResponse {
    /**
     * Handle client found on Firestore
     * @param clientFound
     */
    void onClientResponse(Client clientFound);
}
